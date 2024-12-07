
package DAO;

import Model.Trabajador;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class TrabajadorDAO {
    
    PreparedStatement ps;
    CallableStatement cs;
    ResultSet rs;
    Connection con;
    Conexion cn = new Conexion();

    public List<Trabajador> listarTrabajadores() {
        ArrayList<Trabajador> listar = new ArrayList<>();
        String sql = "{CALL ListarTrabajadores()}";
        try {
            con = cn.getConnection();
            ps = con.prepareCall(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Trabajador tra = new Trabajador();
                tra.setIdTrabajador(rs.getInt(1));
                tra.setNombre(rs.getString(2));
                tra.setApellidos(rs.getString(3));
                tra.setNroIdentificacion(rs.getString(4));
                tra.setEmail(rs.getString(5));
                tra.setDireccion(rs.getString(6));
                tra.setTelefono(rs.getString(7));
                tra.setCargo(rs.getString(8));
                tra.setSueldo(rs.getDouble(9));
                listar.add(tra);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return listar;
    }
    
    
    public void registrarTrabajador(String nombre, String apellidos, String nroIdentificacion, String email, String direccion, String telefono, String cargo, double sueldo) throws SQLException {
        String sql = "{CALL RegistrarCliente(?, ?, ?, ?, ?, ?, ?, ?)}";
        try {
            con = cn.getConnection();
            ps = con.prepareCall(sql);
            ps.setString(1, nombre);
            ps.setString(2, apellidos);
            ps.setString(3, nroIdentificacion);
            ps.setString(4, email);
            ps.setString(5, direccion);
            ps.setString(6, telefono);
            ps.setString(7, cargo);
            ps.setDouble(8, sueldo);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error al registrar el trabajador", e);
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
    
    
}
