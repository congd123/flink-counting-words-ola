/*
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

package com.examples.flink.streaming

import org.apache.flink.api.common.functions.FlatMapFunction
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.util.Collector

object StreamingJobCountOlaWord {
  def main(args: Array[String]) {

    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.enableCheckpointing(10000);

    val dataStream: DataStream[(String, Integer)] = env
        .socketTextStream("192.168.99.1", 9999)
        .flatMap(new FlatMapFunction[String, (String, Integer)] {
          override def flatMap(words: String, collector: Collector[(String, Integer)]): Unit = {
            words.split(" ").filter(_.toLowerCase == "ola").foreach(word => collector.collect((word, 1)))
          }
        })
        .keyBy(0)
        .timeWindow(Time.seconds(5))
        .sum(1);

    dataStream.print();

    env.execute("Counting Ola words");
  }
}