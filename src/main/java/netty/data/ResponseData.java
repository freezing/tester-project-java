package netty.data;

public class ResponseData {
  private final int value;

  public ResponseData(int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }

  @Override
  public String toString() {
    return "ResponseData{" +
        "value=" + value +
        '}';
  }
}
