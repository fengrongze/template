package com.frz.template.mulisource.util;

import com.github.pagehelper.PageInfo;
import com.softisland.common.utils.DateTimeUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;

import java.util.*;

/**
 * Created by fengrongze on 2017/3/6.
 */
public final class QueryJDBCUtils {
    public static final String SELECT_KEY_LIKE = "like";
    public static final String SELECT_KEY_BETWEEN = "between";
    public static final String SELECT_KEY_IN = "in";
    public static final String SELECT_KEY_DATE_BETWEEN = "date_between";
    public static final String SELECT_KEY_MORE_THANEQ = "more_than_equal";
    public static final String SELECT_KEY_MORE_THAN = "more_than";
    public static final String SELECT_KEY_LESS_THANEQ = "less_than_equal";
    public static final String SELECT_KEY_LESS_THAN = "less_than";
    @Autowired
    private JdbcTemplate queryJdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate queryNamedParameterJdbcTemplate;
    @Autowired
    private SimpleJdbcCall querySimpleJdbcCall;

    public QueryJDBCUtils() {
    }

    public int queryForInt(String sql, Object... objects) throws Exception {
        if(StringUtils.isEmpty(sql)) {
            throw new Exception("SQL语句不能为空！");
        } else {
            return ((Integer)this.queryJdbcTemplate.queryForObject(sql, objects, Integer.class)).intValue();
        }
    }

    public int queryForInt(String sql) throws Exception {
        if(StringUtils.isEmpty(sql)) {
            throw new Exception("SQL语句不能为空！");
        } else {
            return ((Integer)this.queryJdbcTemplate.queryForObject(sql, Integer.class)).intValue();
        }
    }

    public Map<String, Object> queryForMap(String sql, boolean isCoverNullToEmpty, Object... objects) throws Exception {
        if(StringUtils.isEmpty(sql)) {
            throw new Exception("SQL语句不能为空！");
        } else {
            SqlRowSet sqlRowSet = this.queryJdbcTemplate.queryForRowSet(sql, objects);
            if(sqlRowSet.next()) {
                SqlRowSetMetaData metaData = sqlRowSet.getMetaData();
                int columnCount = metaData.getColumnCount();

                HashMap map;
                for(map = new HashMap(metaData.getColumnCount()); columnCount > 0; --columnCount) {
                    if(isCoverNullToEmpty) {
                        map.put(metaData.getColumnLabel(columnCount), sqlRowSet.getObject(columnCount) == null?"":sqlRowSet.getObject(columnCount));
                    } else {
                        map.put(metaData.getColumnLabel(columnCount), sqlRowSet.getObject(columnCount));
                    }
                }

                return map;
            } else {
                return new HashMap(0);
            }
        }
    }

    public String queryForStringWhenResultMabyNull(String sql, Object... objects) throws Exception {
        if(StringUtils.isEmpty(sql)) {
            throw new Exception("SQL语句不能为空！");
        } else {
            SqlRowSet sqlRowSet = this.queryJdbcTemplate.queryForRowSet(sql, objects);
            return sqlRowSet.next()?sqlRowSet.getString(1):"";
        }
    }

    public long queryForLong(String sql, Object... objects) throws Exception {
        if(StringUtils.isEmpty(sql)) {
            throw new Exception("SQL语句不能为空！");
        } else {
            return ((Long)this.queryJdbcTemplate.queryForObject(sql, objects, Long.class)).longValue();
        }
    }

    public String queryForString(String sql, Object... objects) throws Exception {
        if(StringUtils.isEmpty(sql)) {
            throw new Exception("SQL语句不能为空！");
        } else {
            return (String)this.queryJdbcTemplate.queryForObject(sql, objects, String.class);
        }
    }

    public int queryForInt(String sql, SqlParameterSource namedParameters) throws Exception {
        if(StringUtils.isEmpty(sql)) {
            throw new Exception("SQL语句不能为空！");
        } else {
            return ((Integer)this.queryNamedParameterJdbcTemplate.queryForObject(sql, namedParameters, Integer.class)).intValue();
        }
    }

    public long queryForLong(String sql, SqlParameterSource namedParameters) throws Exception {
        if(StringUtils.isEmpty(sql)) {
            throw new Exception("SQL语句不能为空！");
        } else {
            return ((Long)this.queryNamedParameterJdbcTemplate.queryForObject(sql, namedParameters, Long.class)).longValue();
        }
    }

    public String queryForString(String sql, SqlParameterSource namedParameters) throws Exception {
        if(StringUtils.isEmpty(sql)) {
            throw new Exception("SQL语句不能为空！");
        } else {
            return (String)this.queryNamedParameterJdbcTemplate.queryForObject(sql, namedParameters, String.class);
        }
    }

    public List<Map<String, Object>> queryForList(String sql, Object... objects) throws Exception {
        if(StringUtils.isEmpty(sql)) {
            throw new Exception("SQL语句不能为空！");
        } else {
            return this.queryJdbcTemplate.queryForList(sql, objects);
        }
    }

    public List<String> queryForListString(String sql, Object... objects) throws Exception {
        return this.queryJdbcTemplate.query(sql, (rs, rowNum) -> {
            return rs.getString(1);
        }, objects);
    }

    public List<Map<String, Object>> queryForList(String sql, SqlParameterSource namedParameters) throws Exception {
        if(StringUtils.isEmpty(sql)) {
            throw new Exception("SQL语句不能为空！");
        } else {
            return this.queryNamedParameterJdbcTemplate.queryForList(sql, namedParameters);
        }
    }

    public PageInfo queryForPageListOrderByAsc(String tableName, Map<String, Object> parameter, int start, int pageSize, String orderByColumn) throws Exception {
        HashMap orderByMap = new HashMap(1);
        orderByMap.put(orderByColumn, "asc");
        return this.queryForPageList(tableName, (String[])null, parameter, orderByMap, start, pageSize);
    }

    public PageInfo queryForPageList(String tableName, Map<String, Object> parameter, int start, int pageSize) throws Exception {
        return this.queryForPageList(tableName, (String[])null, parameter, (Map)null, start, pageSize);
    }

    public PageInfo queryForPageListOrderByDesc(String tableName, Map<String, Object> parameter, int start, int pageSize, String orderByColumn) throws Exception {
        HashMap orderByMap = new HashMap(1);
        orderByMap.put(orderByColumn, "desc");
        return this.queryForPageList(tableName, (String[])null, parameter, orderByMap, start, pageSize);
    }

    public int queryForPageCount(String tableName, StringBuilder whereCondition, MapSqlParameterSource parameterSource) throws Exception {
        StringBuilder sb = new StringBuilder();
        if(tableName.indexOf("select ") > -1) {
            sb.append("select count(*) from (").append(tableName).append(")as A_ where 1=1 ");
        } else {
            sb.append("select count(*) from ").append(tableName).append(" where 1=1 ");
        }

        sb.append(whereCondition);
        return this.queryForInt(sb.toString(), (SqlParameterSource)parameterSource);
    }

    public PageInfo queryForPageList(String tableName, Map<String, Object> parameter, Map<String, String> orderByColumnMap, int start, int pageSize) throws Exception {
        return this.queryForPageList(tableName, (String[])null, parameter, orderByColumnMap, start, pageSize);
    }

    public Map<Object, Object> getTableColumnsType(String tableNameOrSql) throws Exception {
        SqlRowSetMetaData sqlRowSetMetaData;
        if(tableNameOrSql.indexOf("select ") > -1) {
            sqlRowSetMetaData = this.queryJdbcTemplate.queryForRowSet("select * from (" + tableNameOrSql + ")as A_ where 1=2").getMetaData();
        } else {
            sqlRowSetMetaData = this.queryJdbcTemplate.queryForRowSet("select * from " + tableNameOrSql + " where 1=2").getMetaData();
        }

        HashMap columnTypes = new HashMap(sqlRowSetMetaData.getColumnCount());
        int i = 1;
        String[] var5 = sqlRowSetMetaData.getColumnNames();
        int var6 = var5.length;

        for(int var7 = 0; var7 < var6; ++var7) {
            String columnName = var5[var7];
            columnTypes.put(columnName, Integer.valueOf(sqlRowSetMetaData.getColumnType(i)));
            ++i;
        }

        return columnTypes;
    }

    public PageInfo queryForPageList(String tableName, String[] columns, Map<String, Object> parameter, Map<String, String> orderByColumnMap, int start, int pageSize) throws Exception {
        Map columnsTypes = this.getTableColumnsType(tableName);
        StringBuilder sb = new StringBuilder("select ");
        if(null != columns && columns.length != 0) {
            Arrays.asList(columns).forEach((v) -> {
                sb.append(v).append(",");
            });
            sb.delete(sb.length() - 1, sb.length());
        } else {
            sb.append(" * ");
        }

        if(tableName.indexOf("select ") > -1) {
            sb.append(" from (").append(tableName).append(")as A_ where 1=1 ");
        } else {
            sb.append(" from ").append(tableName).append(" where 1=1 ");
        }

        StringBuilder whereCondition = this.joinWhereCondition(parameter);
        sb.append(whereCondition);
        this.appendOrderByCondition(orderByColumnMap, sb);
        sb.append(" limit :pageSize OFFSET :start");
        MapSqlParameterSource parameterSource = this.getCoveredParamValue(parameter, columnsTypes);
        int total = this.queryForPageCount(tableName, whereCondition, parameterSource);
        parameterSource.addValue("pageSize", Integer.valueOf(pageSize < 0?0:pageSize));
        parameterSource.addValue("start", Integer.valueOf(start < 0?0:start));
        List list = this.queryNamedParameterJdbcTemplate.queryForList(sb.toString(), parameterSource);
        return new PageInfo(list, total);
    }

    private MapSqlParameterSource getCoveredParamValue(Map<String, Object> parameter, Map<Object, Object> columnsTypes) throws Exception {
        String[] columnNames = (String[])columnsTypes.keySet().toArray(new String[columnsTypes.keySet().size()]);
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        if(null != parameter && !parameter.isEmpty()) {
            Iterator iterator = parameter.keySet().iterator();

            while(iterator.hasNext()) {
                String columnMix = (String)iterator.next();
                String columnName = columnMix.split("\\$\\$")[0];
                String whereKey = "=";
                if(columnMix.split("\\$\\$").length > 1) {
                    whereKey = columnMix.split("\\$\\$")[1];
                }

                String[] var9 = columnNames;
                int var10 = columnNames.length;

                for(int var11 = 0; var11 < var10; ++var11) {
                    String dataColumnName = var9[var11];
                    if(columnName.equals(dataColumnName)) {
                        int type = ((Integer)columnsTypes.get(columnName)).intValue();
                        String whereValue = parameter.get(columnMix).toString();
                        byte var16 = -1;
                        switch(whereKey.hashCode()) {
                            case -216634360:
                                if(whereKey.equals("between")) {
                                    var16 = 0;
                                }
                                break;
                            case 469333111:
                                if(whereKey.equals("date_between")) {
                                    var16 = 1;
                                }
                        }

                        switch(var16) {
                            case 0:
                            case 1:
                                this.getBetweenParamSource(parameterSource, columnName, type, whereValue);
                                break;
                            default:
                                this.getCommonParamSource(parameterSource, columnName, type, whereValue);
                        }
                    }
                }
            }
        }

        return parameterSource;
    }

    private void getCommonParamSource(MapSqlParameterSource parameterSource, String columnName, int type, String whereValue) throws Exception {
        switch(type) {
            case -5:
            case 4:
            case 5:
                parameterSource.addValue(columnName, Integer.valueOf(Integer.parseInt(whereValue)), type);
                break;
            case -4:
            case -3:
            case -2:
            case -1:
            case 0:
            case 1:
            case 2:
            case 7:
            default:
                parameterSource.addValue(columnName, whereValue, type);
                break;
            case 3:
            case 8:
                parameterSource.addValue(columnName, Double.valueOf(Double.parseDouble(whereValue)), type);
                break;
            case 6:
                parameterSource.addValue(columnName, Float.valueOf(Float.parseFloat(whereValue)), type);
        }

    }

    private void getBetweenParamSource(MapSqlParameterSource parameterSource, String columnName, int type, String whereValue) throws Exception {
        switch(type) {
            case -5:
            case 4:
            case 5:
                parameterSource.addValue(columnName + "_MIN", Integer.valueOf(Integer.parseInt(whereValue.split(";")[0])), type);
                parameterSource.addValue(columnName + "_MAX", Integer.valueOf(Integer.parseInt(whereValue.split(";")[1])), type);
                break;
            case 3:
            case 8:
                parameterSource.addValue(columnName + "_MIN", Double.valueOf(Double.parseDouble(whereValue.split(";")[0])), type);
                parameterSource.addValue(columnName + "_MAX", Double.valueOf(Double.parseDouble(whereValue.split(";")[1])), type);
                break;
            case 6:
                parameterSource.addValue(columnName + "_MIN", Float.valueOf(Float.parseFloat(whereValue.split(";")[0])), type);
                parameterSource.addValue(columnName + "_MAX", Float.valueOf(Float.parseFloat(whereValue.split(";")[1])), type);
                break;
            case 91:
                parameterSource.addValue(columnName + "_MIN", DateTimeUtil.getDateFromStrDate(whereValue.split(";")[0]), type);
                parameterSource.addValue(columnName + "_MAX", DateTimeUtil.getDateFromStrDate(whereValue.split(";")[1]), type);
                break;
            case 92:
            case 93:
                parameterSource.addValue(columnName + "_MIN", DateTimeUtil.getTimeStampByStrDateTime(whereValue.split(";")[0]), type);
                parameterSource.addValue(columnName + "_MAX", DateTimeUtil.getTimeStampByStrDateTime(whereValue.split(";")[1]), type);
                break;
            default:
                parameterSource.addValue(columnName + "_MIN", whereValue.split(";")[0], type);
                parameterSource.addValue(columnName + "_MAX", whereValue.split(";")[1], type);
        }

    }

    public List<Map<String, Object>> queryForOrderBy(String tableName, String[] columns, Map<String, Object> parameter, Map<String, String> orderByColumnMap) throws Exception {
        StringBuilder sb = new StringBuilder("select ");
        if(null != columns && columns.length != 0) {
            Arrays.asList(columns).forEach((v) -> {
                sb.append(v).append(",");
            });
            sb.delete(sb.length() - 1, sb.length());
        } else {
            sb.append(" * ");
        }

        sb.append(" from ").append(tableName).append(" where 1=1 ");
        sb.append(this.joinWhereCondition(parameter));
        this.appendOrderByCondition(orderByColumnMap, sb);
        List list = this.queryNamedParameterJdbcTemplate.queryForList(sb.toString(), this.getCoveredParamValue(parameter, this.getTableColumnsType(tableName)));
        return list;
    }

    private void appendOrderByCondition(Map<String, String> orderByColumnMap, StringBuilder sb) {
        byte i = 0;
        if(null != orderByColumnMap && !orderByColumnMap.isEmpty()) {
            sb.append(" order by ");
            Iterator it = orderByColumnMap.keySet().iterator();

            while(it.hasNext()) {
                String orderKey = (String)it.next();
                sb.append(orderKey).append(" ").append((String)orderByColumnMap.get(orderKey));
                if(i != orderByColumnMap.size() - 1) {
                    sb.append(",");
                } else {
                    sb.append(" ");
                }
            }
        }

    }

    private StringBuilder joinWhereCondition(Map<String, Object> parameter) {
        if(null != parameter && !parameter.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            parameter.keySet().forEach((v) -> {
                sb.append(" and ");
                String[] columnAndSelectKey = v.split("\\$\\$");
                if(columnAndSelectKey.length < 2) {
                    sb.append(columnAndSelectKey[0]);
                    sb.append("= :");
                    sb.append(columnAndSelectKey[0]);
                } else {
                    String key = columnAndSelectKey[1];
                    Object paramValue = parameter.get(v);
                    byte var7 = -1;
                    switch(key.hashCode()) {
                        case -1949465220:
                            if(key.equals("less_than_equal")) {
                                var7 = 3;
                            }
                            break;
                        case -216634360:
                            if(key.equals("between")) {
                                var7 = 5;
                            }
                            break;
                        case 3365:
                            if(key.equals("in")) {
                                var7 = 7;
                            }
                            break;
                        case 3321751:
                            if(key.equals("like")) {
                                var7 = 4;
                            }
                            break;
                        case 365984903:
                            if(key.equals("less_than")) {
                                var7 = 2;
                            }
                            break;
                        case 469333111:
                            if(key.equals("date_between")) {
                                var7 = 6;
                            }
                            break;
                        case 1578679872:
                            if(key.equals("more_than_equal")) {
                                var7 = 0;
                            }
                            break;
                        case 1813420107:
                            if(key.equals("more_than")) {
                                var7 = 1;
                            }
                    }

                    switch(var7) {
                        case 0:
                            sb.append(columnAndSelectKey[0]).append(">=").append(" :").append(columnAndSelectKey[0]);
                            break;
                        case 1:
                            sb.append(columnAndSelectKey[0]).append(">").append(" :").append(columnAndSelectKey[0]);
                            break;
                        case 2:
                            sb.append(columnAndSelectKey[0]).append("<").append(" :").append(columnAndSelectKey[0]);
                            break;
                        case 3:
                            sb.append(columnAndSelectKey[0]).append("<=").append(" :").append(columnAndSelectKey[0]);
                            break;
                        case 4:
                            sb.append(columnAndSelectKey[0]);
                            sb.append(" like concat(\'%\',").append(" :").append(columnAndSelectKey[0]).append(",\'%\')");
                            break;
                        case 5:
                        case 6:
                            sb.append(columnAndSelectKey[0]).append(">=").append(" :").append(columnAndSelectKey[0]).append("_MIN ");
                            sb.append(" and ").append(columnAndSelectKey[0]).append("<").append(" :").append(columnAndSelectKey[0]).append("_MAX ");
                            break;
                        case 7:
                            String insValue = paramValue.toString();
                            sb.append(columnAndSelectKey[0]).append(" in (");
                            if(insValue.indexOf(";") > 0) {
                                String[] values = insValue.split(";");

                                for(int i = 0; i < values.length; ++i) {
                                    sb.append("\'").append(values[i]).append("\'");
                                    if(i != insValue.split(";").length - 1) {
                                        sb.append(",");
                                    }
                                }
                            } else {
                                sb.append("\'").append(insValue).append("\'");
                            }

                            sb.append(")");
                    }
                }

            });
            return sb;
        } else {
            return new StringBuilder();
        }
    }

    public List<Map<String, Object>> queryForList(String sql) throws Exception {
        if(StringUtils.isEmpty(sql)) {
            throw new Exception("SQL语句不能为空！");
        } else {
            return this.queryJdbcTemplate.queryForList(sql);
        }
    }

    public int update(String sql, Object... objects) throws Exception {
        if(StringUtils.isEmpty(sql)) {
            throw new Exception("SQL语句不能为空！");
        } else {
            return this.queryJdbcTemplate.update(sql, objects);
        }
    }

    public int update(Map<String, Object> pramMap, Map<String, String> whereKey, String tableName) throws Exception {
        if(null != pramMap && !pramMap.isEmpty() && null != whereKey && !whereKey.isEmpty() && StringUtils.isNotEmpty(tableName)) {
            Iterator sb = whereKey.keySet().iterator();

            String i;
            do {
                if(!sb.hasNext()) {
                    StringBuilder var9 = (new StringBuilder("update ")).append(tableName).append(" set ");
                    int var10 = 0;

                    for(Iterator tag = pramMap.keySet().iterator(); tag.hasNext(); ++var10) {
                        String parameterSource = (String)tag.next();
                        var9.append(parameterSource).append("= :").append(parameterSource);
                        if(var10 != pramMap.size() - 1) {
                            var9.append(",");
                        }
                    }

                    byte var11 = 0;

                    for(Iterator var12 = whereKey.keySet().iterator(); var12.hasNext(); var11 = 1) {
                        String key = (String)var12.next();
                        if(var11 > 0) {
                            var9.append(" and ").append(key).append("= :").append(key);
                        } else {
                            var9.append(" where ").append(key).append("= :").append(key);
                        }
                    }

                    MapSqlParameterSource var13 = new MapSqlParameterSource();
                    var13.addValues(pramMap);
                    var13.addValues(whereKey);
                    return this.update(var9.toString(), (SqlParameterSource)var13);
                }

                i = (String)sb.next();
            } while(!i.equals(whereKey.get(i)));

            return 0;
        } else {
            return 0;
        }
    }

    public int[] updateBatch(String sql, List<Object[]> list) throws Exception {
        if(StringUtils.isEmpty(sql)) {
            throw new Exception("SQL语句不能为空！");
        } else {
            return this.queryJdbcTemplate.batchUpdate(sql, list);
        }
    }

    public int update(String sql, SqlParameterSource namedParameters) throws Exception {
        if(StringUtils.isEmpty(sql)) {
            throw new Exception("SQL语句不能为空！");
        } else {
            return this.queryNamedParameterJdbcTemplate.update(sql, namedParameters);
        }
    }

    public int[] updateBatch(String sql, SqlParameterSource[] sqlParameterSources) throws Exception {
        if(StringUtils.isEmpty(sql)) {
            throw new Exception("SQL语句不能为空！");
        } else {
            return this.queryNamedParameterJdbcTemplate.batchUpdate(sql, sqlParameterSources);
        }
    }

    public Map callProcedure(String procedureName, SqlParameterSource namedParameters) throws Exception {
        if(StringUtils.isEmpty(procedureName)) {
            throw new Exception("存储过程名称不能为空！");
        } else {
            return this.querySimpleJdbcCall.withProcedureName(procedureName).execute(namedParameters);
        }
    }

    public Map callProcedure(String procedureName, Object... objects) throws Exception {
        if(StringUtils.isEmpty(procedureName)) {
            throw new Exception("存储过程名称不能为空！");
        } else {
            return this.querySimpleJdbcCall.withProcedureName(procedureName).execute(objects);
        }
    }

    public int insert(String tableName, Map<String, Object> parameterValues) throws Exception {
        if(StringUtils.isEmpty(tableName)) {
            throw new Exception("表名不能为空！");
        } else if(null != parameterValues && !parameterValues.isEmpty()) {
            String[] columnNames = (String[])parameterValues.keySet().toArray(new String[parameterValues.size()]);
            return this.getSimpleJdbcInsert().withTableName(tableName).usingColumns(columnNames).execute(parameterValues);
        } else {
            return 0;
        }
    }

    public int insert(String tableName, String[] coluNames, Map<String, Object> parameterValues) throws Exception {
        if(StringUtils.isEmpty(tableName)) {
            throw new Exception("表名不能为空！");
        } else {
            return this.getSimpleJdbcInsert().withTableName(tableName).usingColumns(coluNames).execute(parameterValues);
        }
    }

    public void insertBatch(String tableName, String[] coluNames, Map<String, Object>[] parameterValues) throws Exception {
        if(StringUtils.isEmpty(tableName)) {
            throw new Exception("表名不能为空！");
        } else {
            this.getSimpleJdbcInsert().withTableName(tableName).usingColumns(coluNames).executeBatch(parameterValues);
        }
    }

    public int[] insertBatch(String tableName, Map<String, Object>[] parameterValues) throws Exception {
        if(StringUtils.isEmpty(tableName)) {
            throw new Exception("表名不能为空！");
        } else {
            return this.getSimpleJdbcInsert().withTableName(tableName).executeBatch(parameterValues);
        }
    }

    public int insert(String tableName, Object obj) throws Exception {
        if(StringUtils.isEmpty(tableName)) {
            throw new Exception("表名不能为空！");
        } else {
            BeanPropertySqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(obj);
            return this.getSimpleJdbcInsert().withTableName(tableName).execute(sqlParameterSource);
        }
    }

    public int[] insertBatch(String tableName, Object[] objs) throws Exception {
        if(StringUtils.isEmpty(tableName)) {
            throw new Exception("表名不能为空！");
        } else if(null == objs) {
            throw new Exception("要插入的对象不能为空！");
        } else {
            BeanPropertySqlParameterSource[] sqlParameterSources = new BeanPropertySqlParameterSource[objs.length];
            int index = 0;
            Object[] var5 = objs;
            int var6 = objs.length;

            for(int var7 = 0; var7 < var6; ++var7) {
                Object obj = var5[var7];
                sqlParameterSources[index] = new BeanPropertySqlParameterSource(obj);
                ++index;
            }

            return this.getSimpleJdbcInsert().withTableName(tableName).executeBatch(sqlParameterSources);
        }
    }

    public int insert(String tableName, String[] coluNames, Object obj) throws Exception {
        if(StringUtils.isEmpty(tableName)) {
            throw new Exception("表名不能为空！");
        } else {
            BeanPropertySqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(obj);
            return this.getSimpleJdbcInsert().withTableName(tableName).usingColumns(coluNames).execute(sqlParameterSource);
        }
    }

    private SimpleJdbcInsert getSimpleJdbcInsert() throws Exception {
        return new SimpleJdbcInsert(this.queryJdbcTemplate);
    }

    public List<Map<String, Object>> queryForColumnList(String tableName, String[] columns, Map<String, Object> parameter) throws Exception {
        Map columnsTypes = this.getTableColumnsType(tableName);
        StringBuilder sb = new StringBuilder("select ");
        if(null != columns && columns.length != 0) {
            Arrays.asList(columns).forEach((v) -> {
                sb.append(v).append(",");
            });
            sb.delete(sb.length() - 1, sb.length());
        } else {
            sb.append(" * ");
        }

        if(tableName.indexOf("select ") > -1) {
            sb.append(" from (").append(tableName).append(")as A_ where 1=1 ");
        } else {
            sb.append(" from ").append(tableName).append(" where 1=1 ");
        }

        StringBuilder whereCondition = this.joinWhereCondition(parameter);
        sb.append(whereCondition);
        MapSqlParameterSource parameterSource = this.getCoveredParamValue(parameter, columnsTypes);
        List list = this.queryNamedParameterJdbcTemplate.queryForList(sb.toString(), parameterSource);
        return list;
    }
}
