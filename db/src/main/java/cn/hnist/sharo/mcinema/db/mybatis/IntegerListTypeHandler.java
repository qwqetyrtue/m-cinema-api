package cn.hnist.sharo.mcinema.db.mybatis;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IntegerListTypeHandler implements TypeHandler<Integer[]> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    //用于将java类型设置到预处理语句中
    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, Integer[] integers, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, toJson(integers));
    }

    //将数据库的返回结果转换为java类型
    @Override
    public Integer[] getResult(ResultSet resultSet, String s) throws SQLException {
        return toData(resultSet.getString(s));
    }

    //将数据库的返回结果转换为java类型
    @Override
    public Integer[] getResult(ResultSet resultSet, int i) throws SQLException {
        return toData(resultSet.getString(i));
    }

    //将数据库的返回结果转换为java类型，该方法在存储过程里面使用
    @Override
    public Integer[] getResult(CallableStatement callableStatement, int i) throws SQLException {
        return toData(callableStatement.getString(i));
    }

    private String toJson(Integer[] integer) {
        try {
            return objectMapper.writeValueAsString(integer);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "[]";
        }
    }

    private Integer[] toData(String json){
        try {
            return objectMapper.readValue(json,Integer[].class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new Integer[]{};
        }
    }
}
