package com.mapr.storm;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichSpout;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;
import org.apache.log4j.Logger;

import java.util.Map;
import java.util.Random;

/**
 * Create test data.
 */
public class EventSpout implements IRichSpout {
  private boolean isDistributed;
  private SpoutOutputCollector collector;
  private int n;
  private int emitted = 0;

  public EventSpout(int n) {
    this(true);
    this.n = n;
  }

  public EventSpout(boolean isDistributed) {
    this.isDistributed = isDistributed;
    this.n = 1000;
  }

  public boolean isDistributed() {
    return isDistributed;
  }

  public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
    this.collector = collector;
  }

  public void close() {
  }

  final String[] keys = new String[]{"z1", "z2", "z3", "z4", "z5"};
  final String[] words = new String[]{"nathan", "mike", "jackson", "golda", "bertels"};
  final Random rand = new Random();

  public void nextTuple() {

    if (emitted < n) {
      Utils.sleep(1);
      collector.emit(new Values(keys[rand.nextInt(keys.length)], words[rand.nextInt(words.length)]));
      emitted++;
    } else {
      Utils.sleep(1000);
    }
  }

  public void ack(Object msgId) {

  }

  public void fail(Object msgId) {

  }

  public void declareOutputFields(OutputFieldsDeclarer declarer) {
    declarer.declare(new Fields("key", "word"));
  }
}
