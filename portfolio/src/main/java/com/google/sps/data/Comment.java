package com.google.sps.data;

/** An item on a comment list. */
public final class Comment {

  private final long id;
  private final String text;
  private final double score;

  public Comment(long id, String text, double score) {
    this.id = id;
    this.text = text;
    this.score = score;
  }
  
  public String getText(){
      return this.text;
  }

}