package com.google.sps.data;

/** An item on a comment list. */
public final class Comment {

  private final long id;
  private final String text;

  public Comment(long id, String text) {
    this.id = id;
    this.text = text;
  }
  public String getText(){
      return this.text;
  }
}