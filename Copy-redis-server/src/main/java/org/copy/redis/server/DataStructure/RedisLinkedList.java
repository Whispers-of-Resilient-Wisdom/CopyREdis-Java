package org.copy.redis.server.DataStructure;


import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
@Getter
@Setter
public class RedisLinkedList {
  private LinkedList<String> list;
  public RedisLinkedList() {
      list = new LinkedList<>();
  }
  public RedisLinkedList(LinkedList<String> list) {
      this.list = list;
  }

    @Override
    public String toString() {
        return "RedisLinkedList{" +
                "list=" + list +
                '}';
    }
}


