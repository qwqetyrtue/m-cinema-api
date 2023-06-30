package cn.hnist.sharo.mcinema.db.mybatis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MatrixTypeHandler implements TypeHandler<Integer[][]> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void setParameter(PreparedStatement ps, int i, Integer[][] parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i,toJson(parameter));
    }

    @Override
    public Integer[][] getResult(ResultSet rs, String columnName) throws SQLException {
        return toData(rs.getString(columnName));
    }

    @Override
    public Integer[][] getResult(ResultSet rs, int columnIndex) throws SQLException {
        return toData(rs.getString(columnIndex));
    }

    @Override
    public Integer[][] getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return toData(cs.getString(columnIndex));
    }

    private String toJson(Integer[][] data){
        try {
            return objectMapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "[[]]";
        }
    }

    private Integer[][] toData(String json){
        try {
            return objectMapper.readValue(json,Integer[][].class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new Integer[][]{};
        }
    }
}
