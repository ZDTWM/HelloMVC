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
			// �������û�д�����startʱ
		}
		
		//��һҳ
		int next = start + count;
		
		//��һҳ
		int pre = start - count;
				
		//ĩҳ
		int total = new HeroDAO().getTotal();
		int last;
		
		//���һҳ
		//����������50�����ܹ���5�����ģ���ô���һҳ�Ŀ�ʼ����45
		//����������51�����ܹ���5�����ģ���ô���һҳ�Ŀ�ʼ����50
		if(0 == total % count)
			last = total - count;
		else
			last = total - total % count;
		
		//���pre�Ǹ����ˣ���ʹ��0 
		pre = pre < 0 ? 0 : pre;
		//���next����last����ʹ��last
		next = next > last ? last : next;
		
		request.setAttribute("next", next);
		request.setAttribute("pre", pre);
		request.setAttribute("last", last);
		
		//��ҳ��ѯ
		List<Hero> heros = new HeroDAO().list(start,count);
		request.setAttribute("heros", heros);
		
		request.getRequestDispatcher("listHero.jsp").forward(request, response);
	}
	
}