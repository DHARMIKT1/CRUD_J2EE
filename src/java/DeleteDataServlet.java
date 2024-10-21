import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.*;

public class DeleteDataServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        // Retrieve form parameters
        String uname = request.getParameter("uname");

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pracatical_exam", "root", "");

            String sql = "DELETE FROM insert_db WHERE uname = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, uname);

            int result = ps.executeUpdate();
            if (result > 0) {
                out.println("<h2>User Deleted Successfully!</h2>");
            } else {
                out.println("<h2>User Not Found.</h2>");
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
