package cn.hnist.sharo.mcinema.db.mybatis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StringListTypeHandler implements TypeHandler<String[]> {
    private static final ObjectMapper objectMapper = new ObjectMapper();



    private String toJson(String[] strings) {
        try {
            return objectMapper.writeValueAsString(strings);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "[]";
        }
    }

    private String[] toData(String json){
        try {
            return objectMapper.readValue(json,String[].class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new String[]{};
        }
    }

    @Override
    public void setParameter(PreparedStatement ps, int i, String[] parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i,toJson(parameter));
    }

    @Override
    public String[] getResult(ResultSet rs, String columnName) throws SQLException {
        return toData(rs.getString(columnName));
    }

    @Override
    public String[] getResult(ResultSet rs, int columnIndex) throws SQLException {
        return toData(rs.getString(columnIndex));
    }

    @Override
    public String[] getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return toData(cs.getString(columnIndex));
    }
}
