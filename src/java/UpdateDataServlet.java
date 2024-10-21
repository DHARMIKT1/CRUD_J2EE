import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.*;

public class UpdateDataServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String uname = request.getParameter("uname");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phoneNo = request.getParameter("phoneNo");

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pracatical_exam", "root", "");

            String sql = "UPDATE insert_db SET name = ?, email = ?, phoneNo = ? WHERE uname = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, phoneNo);
            ps.setString(4, uname);

            int result = ps.executeUpdate();
            if (result > 0) {
                out.println("<h2>Data Updated Successfully!</h2>");
            } else {
                out.println("<h2>Data Update Failed. User not found.</h2>");
            }

        } catch (SQLException sqlEx) {
            out.println("<h2>SQL Error: " + sqlEx.getMessage() + "</h2>");
        } catch (ClassNotFoundException cnfEx) {
            out.println("<h2>Driver Not Found: " + cnfEx.getMessage() + "</h2>");
        } finally {
            try {
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException closeEx) {
                out.println("<h2>Error Closing Resources: " + closeEx.getMessage() + "</h2>");
            }
        }
    }
}
