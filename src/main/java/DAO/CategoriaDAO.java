package DAO;

import Model.CategoriaProducto;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {
    PreparedStatement ps;
    CallableStatement cs;
    ResultSet rs;
    Connection con;
    Conexion cn = new Conexion();

    public List<CategoriaProducto> listarCategoria() {
        ArrayList<CategoriaProducto> listar = new ArrayList<>();
        String sql = "{CALL ListarCategoria()}";
        try {
            con = cn.getConnection();
            ps = con.prepareCall(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                CategoriaProducto cat = new CategoriaProducto();
                cat.setIdCategoria(rs.getInt(1));
                cat.setNombreCat(rs.getString(2));
                cat.setDescripcion(rs.getString(3));
                listar.add(cat);
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
    
    
    public boolean registrarCategoria(CategoriaProducto cat) {
        String sql = "{CALL RegistrarCategoria(?, ?)}"; 
        try {
            con = cn.getConnection(); 
            ps = con.prepareCall(sql); 
            ps.setString(1, cat.getNombreCat()); 
            ps.setString(2, cat.getDescripcion()); 
            int resultado = ps.executeUpdate();
            return resultado > 0; 
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
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

    }
}