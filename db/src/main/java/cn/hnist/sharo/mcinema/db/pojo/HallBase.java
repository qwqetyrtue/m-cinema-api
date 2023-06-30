package cn.hnist.sharo.mcinema.db.pojo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class HallBase implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table hall_base
     *
     * @mbg.generated
     */
    public static final Boolean IS_DELETED = Deleted.IS_DELETED.value();

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table hall_base
     *
     * @mbg.generated
     */
    public static final Boolean NOT_DELETED = Deleted.NOT_DELETED.value();

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hall_base.hall_id
     *
     * @mbg.generated
     */
    private Long hallId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hall_base.create_time
     *
     * @mbg.generated
     */
    private LocalDateTime createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hall_base.name
     *
     * @mbg.generated
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hall_base.seat_num
     *
     * @mbg.generated
     */
    private Integer seatNum;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hall_base.hall_type
     *
     * @mbg.generated
     */
    private String hallType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hall_base.deleted
     *
     * @mbg.generated
     */
    private Boolean deleted;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hall_base.seat_arrange
     *
     * @mbg.generated
     */
    private Integer[][] seatArrange;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hall_base.row_num
     *
     * @mbg.generated
     */
    private Integer rowNum;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hall_base.column_num
     *
     * @mbg.generated
     */
    private Integer columnNum;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hall_base.update_time
     *
     * @mbg.generated
     */
    private LocalDateTime updateTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hall_base.location
     *
     * @mbg.generated
     */
    private String location;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hall_base.hall_id_str
     *
     * @mbg.generated
     */
    private String hallIdStr;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table hall_base
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hall_base.hall_id
     *
     * @return the value of hall_base.hall_id
     *
     * @mbg.generated
     */
    public Long getHallId() {
        return hallId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hall_base.hall_id
     *
     * @param hallId the value for hall_base.hall_id
     *
     * @mbg.generated
     */
    public void setHallId(Long hallId) {
        this.hallId = hallId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hall_base.create_time
     *
     * @return the value of hall_base.create_time
     *
     * @mbg.generated
     */
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hall_base.create_time
     *
     * @param createTime the value for hall_base.create_time
     *
     * @mbg.generated
     */
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hall_base.name
     *
     * @return the value of hall_base.name
     *
     * @mbg.generated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hall_base.name
     *
     * @param name the value for hall_base.name
     *
     * @mbg.generated
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hall_base.seat_num
     *
     * @return the value of hall_base.seat_num
     *
     * @mbg.generated
     */
    public Integer getSeatNum() {
        return seatNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hall_base.seat_num
     *
     * @param seatNum the value for hall_base.seat_num
     *
     * @mbg.generated
     */
    public void setSeatNum(Integer seatNum) {
        this.seatNum = seatNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hall_base.hall_type
     *
     * @return the value of hall_base.hall_type
     *
     * @mbg.generated
     */
    public String getHallType() {
        return hallType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hall_base.hall_type
     *
     * @param hallType the value for hall_base.hall_type
     *
     * @mbg.generated
     */
    public void setHallType(String hallType) {
        this.hallType = hallType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hall_base
     *
     * @mbg.generated
     */
    public void andLogicalDeleted(boolean deleted) {
        setDeleted(deleted ? Deleted.IS_DELETED.value() : Deleted.NOT_DELETED.value());
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hall_base.deleted
     *
     * @return the value of hall_base.deleted
     *
     * @mbg.generated
     */
    public Boolean getDeleted() {
        return deleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hall_base.deleted
     *
     * @param deleted the value for hall_base.deleted
     *
     * @mbg.generated
     */
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hall_base.seat_arrange
     *
     * @return the value of hall_base.seat_arrange
     *
     * @mbg.generated
     */
    public Integer[][] getSeatArrange() {
        return seatArrange;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hall_base.seat_arrange
     *
     * @param seatArrange the value for hall_base.seat_arrange
     *
     * @mbg.generated
     */
    public void setSeatArrange(Integer[][] seatArrange) {
        this.seatArrange = seatArrange;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hall_base.row_num
     *
     * @return the value of hall_base.row_num
     *
     * @mbg.generated
     */
    public Integer getRowNum() {
        return rowNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hall_base.row_num
     *
     * @param rowNum the value for hall_base.row_num
     *
     * @mbg.generated
     */
    public void setRowNum(Integer rowNum) {
        this.rowNum = rowNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hall_base.column_num
     *
     * @return the value of hall_base.column_num
     *
     * @mbg.generated
     */
    public Integer getColumnNum() {
        return columnNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hall_base.column_num
     *
     * @param columnNum the value for hall_base.column_num
     *
     * @mbg.generated
     */
    public void setColumnNum(Integer columnNum) {
        this.columnNum = columnNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hall_base.update_time
     *
     * @return the value of hall_base.update_time
     *
     * @mbg.generated
     */
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hall_base.update_time
     *
     * @param updateTime the value for hall_base.update_time
     *
     * @mbg.generated
     */
    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hall_base.location
     *
     * @return the value of hall_base.location
     *
     * @mbg.generated
     */
    public String getLocation() {
        return location;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hall_base.location
     *
     * @param location the value for hall_base.location
     *
     * @mbg.generated
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hall_base.hall_id_str
     *
     * @return the value of hall_base.hall_id_str
     *
     * @mbg.generated
     */
    public String getHallIdStr() {
        return hallIdStr;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hall_base.hall_id_str
     *
     * @param hallIdStr the value for hall_base.hall_id_str
     *
     * @mbg.generated
     */
    public void setHallIdStr(String hallIdStr) {
        this.hallIdStr = hallIdStr;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hall_base
     *
     * @mbg.generated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", IS_DELETED=").append(IS_DELETED);
        sb.append(", NOT_DELETED=").append(NOT_DELETED);
        sb.append(", hallId=").append(hallId);
        sb.append(", createTime=").append(createTime);
        sb.append(", name=").append(name);
        sb.append(", seatNum=").append(seatNum);
        sb.append(", hallType=").append(hallType);
        sb.append(", deleted=").append(deleted);
        sb.append(", seatArrange=").append(seatArrange);
        sb.append(", rowNum=").append(rowNum);
        sb.append(", columnNum=").append(columnNum);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", location=").append(location);
        sb.append(", hallIdStr=").append(hallIdStr);
        sb.append("]");
        return sb.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hall_base
     *
     * @mbg.generated
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        HallBase other = (HallBase) that;
        return (this.getHallId() == null ? other.getHallId() == null : this.getHallId().equals(other.getHallId()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getSeatNum() == null ? other.getSeatNum() == null : this.getSeatNum().equals(other.getSeatNum()))
            && (this.getHallType() == null ? other.getHallType() == null : this.getHallType().equals(other.getHallType()))
            && (this.getDeleted() == null ? other.getDeleted() == null : this.getDeleted().equals(other.getDeleted()))
            && (Arrays.equals(this.getSeatArrange(), other.getSeatArrange()))
            && (this.getRowNum() == null ? other.getRowNum() == null : this.getRowNum().equals(other.getRowNum()))
            && (this.getColumnNum() == null ? other.getColumnNum() == null : this.getColumnNum().equals(other.getColumnNum()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getLocation() == null ? other.getLocation() == null : this.getLocation().equals(other.getLocation()))
            && (this.getHallIdStr() == null ? other.getHallIdStr() == null : this.getHallIdStr().equals(other.getHallIdStr()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hall_base
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getHallId() == null) ? 0 : getHallId().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getSeatNum() == null) ? 0 : getSeatNum().hashCode());
        result = prime * result + ((getHallType() == null) ? 0 : getHallType().hashCode());
        result = prime * result + ((getDeleted() == null) ? 0 : getDeleted().hashCode());
        result = prime * result + (Arrays.hashCode(getSeatArrange()));
        result = prime * result + ((getRowNum() == null) ? 0 : getRowNum().hashCode());
        result = prime * result + ((getColumnNum() == null) ? 0 : getColumnNum().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getLocation() == null) ? 0 : getLocation().hashCode());
        result = prime * result + ((getHallIdStr() == null) ? 0 : getHallIdStr().hashCode());
        return result;
    }

    /**
     * This enum was generated by MyBatis Generator.
     * This enum corresponds to the database table hall_base
     *
     * @mbg.generated
     */
    public enum Deleted {
        NOT_DELETED(new Boolean("0"), "未删除"),
        IS_DELETED(new Boolean("1"), "已删除");

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table hall_base
         *
         * @mbg.generated
         */
        private final Boolean value;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table hall_base
         *
         * @mbg.generated
         */
        private final String name;

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table hall_base
         *
         * @mbg.generated
         */
        Deleted(Boolean value, String name) {
            this.value = value;
            this.name = name;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table hall_base
         *
         * @mbg.generated
         */
        public Boolean getValue() {
            return this.value;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table hall_base
         *
         * @mbg.generated
         */
        public Boolean value() {
            return this.value;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table hall_base
         *
         * @mbg.generated
         */
        public String getName() {
            return this.name;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table hall_base
         *
         * @mbg.generated
         */
        public static Deleted parseValue(Boolean value) {
            if (value != null) {
                for (Deleted item : values()) {
                    if (item.value.equals(value)) {
                        return item;
                    }
                }
            }
            return null;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table hall_base
         *
         * @mbg.generated
         */
        public static Deleted parseName(String name) {
            if (name != null) {
                for (Deleted item : values()) {
                    if (item.name.equals(name)) {
                        return item;
                    }
                }
            }
            return null;
        }
    }

    /**
     * This enum was generated by MyBatis Generator.
     * This enum corresponds to the database table hall_base
     *
     * @mbg.generated
     */
    public enum Column {
        hallId("hall_id", "hallId", "BIGINT", false),
        createTime("create_time", "createTime", "TIMESTAMP", false),
        name("name", "name", "VARCHAR", true),
        seatNum("seat_num", "seatNum", "INTEGER", false),
        hallType("hall_type", "hallType", "VARCHAR", false),
        deleted("deleted", "deleted", "BIT", false),
        seatArrange("seat_arrange", "seatArrange", "VARCHAR", false),
        rowNum("row_num", "rowNum", "INTEGER", false),
        columnNum("column_num", "columnNum", "INTEGER", false),
        updateTime("update_time", "updateTime", "TIMESTAMP", false),
        location("location", "location", "VARCHAR", true),
        hallIdStr("hall_id_str", "hallIdStr", "VARCHAR", false);

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table hall_base
         *
         * @mbg.generated
         */
        private static final String BEGINNING_DELIMITER = "`";

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table hall_base
         *
         * @mbg.generated
         */
        private static final String ENDING_DELIMITER = "`";

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table hall_base
         *
         * @mbg.generated
         */
        private final String column;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table hall_base
         *
         * @mbg.generated
         */
        private final boolean isColumnNameDelimited;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table hall_base
         *
         * @mbg.generated
         */
        private final String javaProperty;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table hall_base
         *
         * @mbg.generated
         */
        private final String jdbcType;

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table hall_base
         *
         * @mbg.generated
         */
        public String value() {
            return this.column;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table hall_base
         *
         * @mbg.generated
         */
        public String getValue() {
            return this.column;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table hall_base
         *
         * @mbg.generated
         */
        public String getJavaProperty() {
            return this.javaProperty;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table hall_base
         *
         * @mbg.generated
         */
        public String getJdbcType() {
            return this.jdbcType;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table hall_base
         *
         * @mbg.generated
         */
        Column(String column, String javaProperty, String jdbcType, boolean isColumnNameDelimited) {
            this.column = column;
            this.javaProperty = javaProperty;
            this.jdbcType = jdbcType;
            this.isColumnNameDelimited = isColumnNameDelimited;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table hall_base
         *
         * @mbg.generated
         */
        public String desc() {
            return this.getEscapedColumnName() + " DESC";
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table hall_base
         *
         * @mbg.generated
         */
        public String asc() {
            return this.getEscapedColumnName() + " ASC";
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table hall_base
         *
         * @mbg.generated
         */
        public static Column[] excludes(Column ... excludes) {
            ArrayList<Column> columns = new ArrayList<>(Arrays.asList(Column.values()));
            if (excludes != null && excludes.length > 0) {
                columns.removeAll(new ArrayList<>(Arrays.asList(excludes)));
            }
            return columns.toArray(new Column[]{});
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table hall_base
         *
         * @mbg.generated
         */
        public static Column[] all() {
            return Column.values();
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table hall_base
         *
         * @mbg.generated
         */
        public String getEscapedColumnName() {
            if (this.isColumnNameDelimited) {
                return new StringBuilder().append(BEGINNING_DELIMITER).append(this.column).append(ENDING_DELIMITER).toString();
            } else {
                return this.column;
            }
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table hall_base
         *
         * @mbg.generated
         */
        public String getAliasedEscapedColumnName() {
            return this.getEscapedColumnName();
        }
    }
}