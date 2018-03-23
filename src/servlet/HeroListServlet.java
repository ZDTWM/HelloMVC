package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Hero;
import dao.HeroDAO;

public class HeroListServlet extends HttpServlet{
	
	protected void service(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		response.setContentType("text/html;charset=UTF-8");
		
		int start = 0;
		int count = 5;
		
		try {
			start = Integer.parseInt(request.getParameter("start"));
		} catch (NumberFormatException e) {
			// 当浏览器没有传参数start时
		}
		
		//下一页
		int next = start + count;
		
		//上一页
		int pre = start - count;
				
		//末页
		int total = new HeroDAO().getTotal();
		int last;
		
		//最后一页
		//假设总数是50，是能够被5整除的，那么最后一页的开始就是45
		//假设总数是51，不能够被5整除的，那么最后一页的开始就是50
		if(0 == total % count)
			last = total - count;
		else
			last = total - total % count;
		
		//如果pre是负数了，就使用0 
		pre = pre < 0 ? 0 : pre;
		//如果next大于last，就使用last
		next = next > last ? last : next;
		
		request.setAttribute("next", next);
		request.setAttribute("pre", pre);
		request.setAttribute("last", last);
		
		//分页查询
		List<Hero> heros = new HeroDAO().list(start,count);
		request.setAttribute("heros", heros);
		
		request.getRequestDispatcher("listHero.jsp").forward(request, response);
	}
	
}
