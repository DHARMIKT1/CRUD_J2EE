import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.*;

/**
 * @author Lenovo
 */
public class InsertDataServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<html><body><h2>Request Processed</h2></body></html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        // Retrieve form parameters
        String uname = request.getParameter("uname");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phoneNo = request.getParameter("phoneNo");
        String psw = request.getParameter("psw");

        // Database connection and insertion logic
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            // Load JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the connection
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pracatical_exam", "root", "");

            // Prepare SQL insert statement
            String sql = "INSERT INTO insert_db (uname, name, email, phoneNo, password) VALUES (?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);

            // Set parameters
            ps.setString(1, uname);
            ps.setString(2, name);
            ps.setString(3, email);
            ps.setString(4, phoneNo);
            ps.setString(5, psw);

            // Execute the insert operation
            int result = ps.executeUpdate();
            if (result > 0) {
                out.println("<h2>Data Inserted Successfully!</h2>");
            } else {
                out.println("<h2>Data Insertion Failed.</h2>");
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

    @Override
    public String getServletInfo() {
        return "Insert Data Servlet";
    }
}
