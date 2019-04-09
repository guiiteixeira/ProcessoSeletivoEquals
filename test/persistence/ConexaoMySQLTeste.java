package persistence;


import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class ConexaoMySQLTeste {

    @Test
    public void conectar(){
        Connection conn = ConexaoMySQL.getConexaoMySQL();
        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


}
