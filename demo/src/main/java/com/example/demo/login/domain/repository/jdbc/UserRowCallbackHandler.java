package com.example.demo.login.domain.repository.jdbc;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowCallbackHandler;

public class UserRowCallbackHandler implements RowCallbackHandler {

    @Override
    public void processRow(ResultSet rs) throws SQLException {

        try {
            File file = new File("sample.csv");
            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
            BufferedWriter bw = new BufferedWriter(osw);

            do {
                // ファイル編集
                String str = rs.getString("user_id") + ","
                        + rs.getString("password") + ","
                        + rs.getString("user_name") + ","
                        + rs.getString("birthday") + ","
                        + rs.getString("age") + ","
                        + rs.getString("marriage") + ","
                        + rs.getString("role");

                // ファイル書き込み
                bw.write(str);
                bw.newLine();
            } while(rs.next());

            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new SQLException(e);
        }
    }

}
