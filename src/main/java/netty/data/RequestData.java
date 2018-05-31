package netty.data;

public class RequestData {
  private final int value;
  private final String name;

  public RequestData(int value, String name) {
    this.value = value;
    this.name = name;
  }

  public int getValue() {
    return value;
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return "RequestData{" +
        "value=" + value +
        ", name='" + name + '\'' +
        '}';
  }
}
