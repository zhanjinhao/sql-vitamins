package cn.addenda.sql.vitamin.rewriter;

import com.alibaba.druid.pool.WrapperAdapter;

import java.sql.*;

public class SqlInterceptedStatement extends WrapperAdapter implements Statement {

  private final SqlInterceptedConnection sqlInterceptedConnection;
  protected final Statement delegate;
  protected final SqlInterceptor sqlInterceptor;

  public SqlInterceptedStatement(SqlInterceptedConnection sqlInterceptedConnection, Statement delegate, SqlInterceptor sqlInterceptor) {
    this.sqlInterceptedConnection = sqlInterceptedConnection;
    this.delegate = delegate;
    this.sqlInterceptor = sqlInterceptor;
  }

  @Override
  public ResultSet executeQuery(String sql) throws SQLException {
    sql = sqlInterceptor.rewrite(sql);
    return delegate.executeQuery(sql);
  }

  @Override
  public int executeUpdate(String sql) throws SQLException {
    sql = sqlInterceptor.rewrite(sql);
    return delegate.executeUpdate(sql);
  }

  @Override
  public void close() throws SQLException {
    delegate.close();
  }

  @Override
  public int getMaxFieldSize() throws SQLException {
    return delegate.getMaxFieldSize();
  }

  @Override
  public void setMaxFieldSize(int max) throws SQLException {
    delegate.setMaxFieldSize(max);
  }

  @Override
  public int getMaxRows() throws SQLException {
    return delegate.getMaxRows();
  }

  @Override
  public void setMaxRows(int max) throws SQLException {
    delegate.setMaxRows(max);
  }

  @Override
  public void setEscapeProcessing(boolean enable) throws SQLException {
    delegate.setEscapeProcessing(enable);
  }

  @Override
  public int getQueryTimeout() throws SQLException {
    return delegate.getQueryTimeout();
  }

  @Override
  public void setQueryTimeout(int seconds) throws SQLException {
    delegate.setQueryTimeout(seconds);
  }

  @Override
  public void cancel() throws SQLException {
    delegate.cancel();
  }

  @Override
  public SQLWarning getWarnings() throws SQLException {
    return delegate.getWarnings();
  }

  @Override
  public void clearWarnings() throws SQLException {
    delegate.clearWarnings();
  }

  @Override
  public void setCursorName(String name) throws SQLException {
    delegate.setCursorName(name);
  }

  @Override
  public boolean execute(String sql) throws SQLException {
    sql = sqlInterceptor.rewrite(sql);
    return delegate.execute(sql);
  }

  @Override
  public ResultSet getResultSet() throws SQLException {
    return delegate.getResultSet();
  }

  @Override
  public int getUpdateCount() throws SQLException {
    return delegate.getUpdateCount();
  }

  @Override
  public boolean getMoreResults() throws SQLException {
    return delegate.getMoreResults();
  }

  @Override
  public void setFetchDirection(int direction) throws SQLException {
    delegate.setFetchDirection(direction);
  }

  @Override
  public int getFetchDirection() throws SQLException {
    return delegate.getFetchDirection();
  }

  @Override
  public void setFetchSize(int rows) throws SQLException {
    delegate.setFetchSize(rows);
  }

  @Override
  public int getFetchSize() throws SQLException {
    return delegate.getFetchSize();
  }

  @Override
  public int getResultSetConcurrency() throws SQLException {
    return delegate.getResultSetConcurrency();
  }

  @Override
  public int getResultSetType() throws SQLException {
    return delegate.getResultSetType();
  }

  @Override
  public void addBatch(String sql) throws SQLException {
    sql = sqlInterceptor.rewrite(sql);
    delegate.addBatch(sql);
  }

  @Override
  public void clearBatch() throws SQLException {
    delegate.clearBatch();
  }

  @Override
  public int[] executeBatch() throws SQLException {
    return delegate.executeBatch();
  }

  @Override
  public Connection getConnection() throws SQLException {
    return sqlInterceptedConnection;
  }

  @Override
  public boolean getMoreResults(int current) throws SQLException {
    return delegate.getMoreResults(current);
  }

  @Override
  public ResultSet getGeneratedKeys() throws SQLException {
    return delegate.getGeneratedKeys();
  }

  @Override
  public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
    sql = sqlInterceptor.rewrite(sql);
    return delegate.executeUpdate(sql, autoGeneratedKeys);
  }

  @Override
  public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
    sql = sqlInterceptor.rewrite(sql);
    return delegate.executeUpdate(sql, columnIndexes);
  }

  @Override
  public int executeUpdate(String sql, String[] columnNames) throws SQLException {
    sql = sqlInterceptor.rewrite(sql);
    return delegate.executeUpdate(sql, columnNames);
  }

  @Override
  public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
    sql = sqlInterceptor.rewrite(sql);
    return delegate.execute(sql, autoGeneratedKeys);
  }

  @Override
  public boolean execute(String sql, int[] columnIndexes) throws SQLException {
    sql = sqlInterceptor.rewrite(sql);
    return delegate.execute(sql, columnIndexes);
  }

  @Override
  public boolean execute(String sql, String[] columnNames) throws SQLException {
    sql = sqlInterceptor.rewrite(sql);
    return delegate.execute(sql, columnNames);
  }

  @Override
  public int getResultSetHoldability() throws SQLException {
    return delegate.getResultSetHoldability();
  }

  @Override
  public boolean isClosed() throws SQLException {
    return delegate.isClosed();
  }

  @Override
  public void setPoolable(boolean poolable) throws SQLException {
    delegate.setPoolable(poolable);
  }

  @Override
  public boolean isPoolable() throws SQLException {
    return delegate.isPoolable();
  }

  @Override
  public void closeOnCompletion() throws SQLException {
    delegate.closeOnCompletion();
  }

  @Override
  public boolean isCloseOnCompletion() throws SQLException {
    return delegate.isCloseOnCompletion();
  }

  @Override
  public long getLargeUpdateCount() throws SQLException {
    return delegate.getLargeUpdateCount();
  }

  @Override
  public void setLargeMaxRows(long max) throws SQLException {
    delegate.setLargeMaxRows(max);
  }

  @Override
  public long getLargeMaxRows() throws SQLException {
    return delegate.getLargeMaxRows();
  }

  @Override
  public long[] executeLargeBatch() throws SQLException {
    return delegate.executeLargeBatch();
  }

  @Override
  public long executeLargeUpdate(String sql) throws SQLException {
    sql = sqlInterceptor.rewrite(sql);
    return delegate.executeLargeUpdate(sql);
  }

  @Override
  public long executeLargeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
    sql = sqlInterceptor.rewrite(sql);
    return delegate.executeLargeUpdate(sql, autoGeneratedKeys);
  }

  @Override
  public long executeLargeUpdate(String sql, int[] columnIndexes) throws SQLException {
    sql = sqlInterceptor.rewrite(sql);
    return delegate.executeLargeUpdate(sql, columnIndexes);
  }

  @Override
  public long executeLargeUpdate(String sql, String[] columnNames) throws SQLException {
    sql = sqlInterceptor.rewrite(sql);
    return delegate.executeLargeUpdate(sql, columnNames);
  }

}
