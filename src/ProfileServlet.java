

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import customTools.DbUser;
import model.BhUser;

/**
 * Servlet implementation class ProfileServlet
 */
@WebServlet("/ProfileServlet")
public class ProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProfileServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		 /*
		 * simplify this so that it always requires two parameters, userid and action
		 * action is view or edit. If edit then the userID of the session(user) must be same as userID for profile
		 * since you can only edit your own.
		 * all urls coming to this page must contain both parameters or get error.
		 */
		 HttpSession session = request.getSession();
		 String nextURL = "/error.jsp";
		 long userid = 0;
		 String action = "";
		 BhUser profileUser = null;
		 BhUser loggedInUser = null;
		 
		 //get user out of session. If they don't exist then send them back to the login page.
		 //kill the session while you're at it.
		 if (session.getAttribute("user")==null){
		 //http://stackoverflow.com/questions/13638446/checking-servlet-session-attribute-value-in-jsp-file
		 nextURL = "/login.jsp";
		 session.invalidate();
		 response.sendRedirect(request.getContextPath() + nextURL);
		 return;//return prevents an error
		 }
		 
		 
		 try{
		 userid = Long.parseLong(request.getParameter("userid"));
		 action = request.getParameter("action");
		 
		 
		 //update profile for user in request variable if action = updateprofile
		 if (request.getParameter("action").equals("updateprofile")){
		 long uid = Long.parseLong(request.getParameter("userid"));
		 String userEmail = request.getParameter("useremail");
		 String userMotto = request.getParameter("usermotto");
		 //populate the existing user then change some fields
		 BhUser updateUser = DbUser.getUser(uid);
		 updateUser.setMotto(userMotto);
		 updateUser.setUseremail(userEmail);
		 //alternatively could modify DbUser.update to take an object of type BhUser
		 DbUser.update(updateUser);
		 }
		 
		 
		 
		 
		 //get the user from the parameter
		 profileUser = DbUser.getUser(userid);
		 //get the current user out of the session
		 loggedInUser = (BhUser) session.getAttribute("user"); 
		 
		 if (profileUser.getBhuserid()==loggedInUser.getBhuserid()){
		 //display profile as form
		 //the session variable editProfile is used by the JSP to
		 //display the profile in edit mode
		 session.setAttribute("editProfile", true);
		 }else{
		 //display profile read-only
		 //the session variable editProfile is used by the JSP to
		 //display the profile in read-only mode
		 session.setAttribute("editProfile", false);
		 }
		 
		 //populate the data in the attributes
		 int imgSize = 120;
		 SimpleDateFormat sdf = new SimpleDateFormat("MMM d, yyyy");
		 String joindate = sdf.format(profileUser.getJoindate());
		 request.setAttribute("userid", profileUser.getBhuserid());
		 request.setAttribute("userimage",DbUser.getGravatarURL(profileUser.getUseremail(), imgSize));
		 request.setAttribute("username", profileUser.getUsername());
		 request.setAttribute("useremail", profileUser.getUseremail());
		 request.setAttribute("usermotto", profileUser.getMotto());
		 request.setAttribute("userjoindate", joindate);
		 nextURL = "/profile.jsp";
		 
		 
		 }catch(Exception e){
		 System.out.println(e);
		 }finally{
		 //redirect to next page as indicated by the value of the nextURL variable
		 getServletContext().getRequestDispatcher(nextURL).forward(request,response);
		 }
	}

}
