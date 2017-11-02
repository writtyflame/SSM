/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.smartdata.metastore.dao;

import org.smartdata.model.SmartFileCompressionInfo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * CompressionFileDao.
 */
public class CompressionFileDao {
  private String TABLE_NAME = "compression_file";

  private DataSource dataSource;

  public void setDataSource(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  public CompressionFileDao(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  public void insert(SmartFileCompressionInfo compressionInfo) {
    SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(dataSource);
    simpleJdbcInsert.setTableName(TABLE_NAME);
    simpleJdbcInsert.execute(toMap(compressionInfo));
  }

  public void deleteByName(String fileName) {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    final String sql = "DELETE FROM " + TABLE_NAME + " WHERE file_name = ?";
    jdbcTemplate.update(sql, fileName);
  }

  private Map<String, Object> toMap(SmartFileCompressionInfo compressionInfo) {
    Map<String, Object> parameters = new HashMap<>();
    parameters.put("file_name", compressionInfo.getFileName());
    parameters.put("buffer_size", compressionInfo.getBufferSize());
    return parameters;
  }
}
