// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: server.proto

package io.freezing.benchmark;

public final class Server {
  private Server() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  public interface BenchmarkRequestOrBuilder extends
      // @@protoc_insertion_point(interface_extends:io.freezing.benchmark.BenchmarkRequest)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>optional int32 id = 1;</code>
     */
    int getId();

    /**
     * <code>optional int32 server_payload_size_bytes = 2;</code>
     */
    int getServerPayloadSizeBytes();

    /**
     * <code>optional int32 server_work_iterations = 3;</code>
     */
    int getServerWorkIterations();

    /**
     * <code>optional int32 server_processing_iterations = 4;</code>
     */
    int getServerProcessingIterations();

    /**
     * <code>optional bytes payload = 5;</code>
     */
    com.google.protobuf.ByteString getPayload();
  }
  /**
   * Protobuf type {@code io.freezing.benchmark.BenchmarkRequest}
   */
  public  static final class BenchmarkRequest extends
      com.google.protobuf.GeneratedMessage implements
      // @@protoc_insertion_point(message_implements:io.freezing.benchmark.BenchmarkRequest)
      BenchmarkRequestOrBuilder {
    // Use BenchmarkRequest.newBuilder() to construct.
    private BenchmarkRequest(com.google.protobuf.GeneratedMessage.Builder builder) {
      super(builder);
    }
    private BenchmarkRequest() {
      id_ = 0;
      serverPayloadSizeBytes_ = 0;
      serverWorkIterations_ = 0;
      serverProcessingIterations_ = 0;
      payload_ = com.google.protobuf.ByteString.EMPTY;
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return com.google.protobuf.UnknownFieldSet.getDefaultInstance();
    }
    private BenchmarkRequest(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry) {
      this();
      int mutable_bitField0_ = 0;
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            default: {
              if (!input.skipField(tag)) {
                done = true;
              }
              break;
            }
            case 8: {

              id_ = input.readInt32();
              break;
            }
            case 16: {

              serverPayloadSizeBytes_ = input.readInt32();
              break;
            }
            case 24: {

              serverWorkIterations_ = input.readInt32();
              break;
            }
            case 32: {

              serverProcessingIterations_ = input.readInt32();
              break;
            }
            case 42: {

              payload_ = input.readBytes();
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw new RuntimeException(e.setUnfinishedMessage(this));
      } catch (java.io.IOException e) {
        throw new RuntimeException(
            new com.google.protobuf.InvalidProtocolBufferException(
                e.getMessage()).setUnfinishedMessage(this));
      } finally {
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return io.freezing.benchmark.Server.internal_static_io_freezing_benchmark_BenchmarkRequest_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return io.freezing.benchmark.Server.internal_static_io_freezing_benchmark_BenchmarkRequest_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              io.freezing.benchmark.Server.BenchmarkRequest.class, io.freezing.benchmark.Server.BenchmarkRequest.Builder.class);
    }

    public static final int ID_FIELD_NUMBER = 1;
    private int id_;
    /**
     * <code>optional int32 id = 1;</code>
     */
    public int getId() {
      return id_;
    }

    public static final int SERVER_PAYLOAD_SIZE_BYTES_FIELD_NUMBER = 2;
    private int serverPayloadSizeBytes_;
    /**
     * <code>optional int32 server_payload_size_bytes = 2;</code>
     */
    public int getServerPayloadSizeBytes() {
      return serverPayloadSizeBytes_;
    }

    public static final int SERVER_WORK_ITERATIONS_FIELD_NUMBER = 3;
    private int serverWorkIterations_;
    /**
     * <code>optional int32 server_work_iterations = 3;</code>
     */
    public int getServerWorkIterations() {
      return serverWorkIterations_;
    }

    public static final int SERVER_PROCESSING_ITERATIONS_FIELD_NUMBER = 4;
    private int serverProcessingIterations_;
    /**
     * <code>optional int32 server_processing_iterations = 4;</code>
     */
    public int getServerProcessingIterations() {
      return serverProcessingIterations_;
    }

    public static final int PAYLOAD_FIELD_NUMBER = 5;
    private com.google.protobuf.ByteString payload_;
    /**
     * <code>optional bytes payload = 5;</code>
     */
    public com.google.protobuf.ByteString getPayload() {
      return payload_;
    }

    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (id_ != 0) {
        output.writeInt32(1, id_);
      }
      if (serverPayloadSizeBytes_ != 0) {
        output.writeInt32(2, serverPayloadSizeBytes_);
      }
      if (serverWorkIterations_ != 0) {
        output.writeInt32(3, serverWorkIterations_);
      }
      if (serverProcessingIterations_ != 0) {
        output.writeInt32(4, serverProcessingIterations_);
      }
      if (!payload_.isEmpty()) {
        output.writeBytes(5, payload_);
      }
    }

    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;

      size = 0;
      if (id_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(1, id_);
      }
      if (serverPayloadSizeBytes_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(2, serverPayloadSizeBytes_);
      }
      if (serverWorkIterations_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(3, serverWorkIterations_);
      }
      if (serverProcessingIterations_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(4, serverProcessingIterations_);
      }
      if (!payload_.isEmpty()) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(5, payload_);
      }
      memoizedSerializedSize = size;
      return size;
    }

    private static final long serialVersionUID = 0L;
    public static io.freezing.benchmark.Server.BenchmarkRequest parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static io.freezing.benchmark.Server.BenchmarkRequest parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static io.freezing.benchmark.Server.BenchmarkRequest parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static io.freezing.benchmark.Server.BenchmarkRequest parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static io.freezing.benchmark.Server.BenchmarkRequest parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static io.freezing.benchmark.Server.BenchmarkRequest parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }
    public static io.freezing.benchmark.Server.BenchmarkRequest parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input);
    }
    public static io.freezing.benchmark.Server.BenchmarkRequest parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input, extensionRegistry);
    }
    public static io.freezing.benchmark.Server.BenchmarkRequest parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static io.freezing.benchmark.Server.BenchmarkRequest parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }

    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(io.freezing.benchmark.Server.BenchmarkRequest prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code io.freezing.benchmark.BenchmarkRequest}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:io.freezing.benchmark.BenchmarkRequest)
        io.freezing.benchmark.Server.BenchmarkRequestOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return io.freezing.benchmark.Server.internal_static_io_freezing_benchmark_BenchmarkRequest_descriptor;
      }

      protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return io.freezing.benchmark.Server.internal_static_io_freezing_benchmark_BenchmarkRequest_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                io.freezing.benchmark.Server.BenchmarkRequest.class, io.freezing.benchmark.Server.BenchmarkRequest.Builder.class);
      }

      // Construct using io.freezing.benchmark.Server.BenchmarkRequest.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessage.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
        }
      }
      public Builder clear() {
        super.clear();
        id_ = 0;

        serverPayloadSizeBytes_ = 0;

        serverWorkIterations_ = 0;

        serverProcessingIterations_ = 0;

        payload_ = com.google.protobuf.ByteString.EMPTY;

        return this;
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return io.freezing.benchmark.Server.internal_static_io_freezing_benchmark_BenchmarkRequest_descriptor;
      }

      public io.freezing.benchmark.Server.BenchmarkRequest getDefaultInstanceForType() {
        return io.freezing.benchmark.Server.BenchmarkRequest.getDefaultInstance();
      }

      public io.freezing.benchmark.Server.BenchmarkRequest build() {
        io.freezing.benchmark.Server.BenchmarkRequest result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public io.freezing.benchmark.Server.BenchmarkRequest buildPartial() {
        io.freezing.benchmark.Server.BenchmarkRequest result = new io.freezing.benchmark.Server.BenchmarkRequest(this);
        result.id_ = id_;
        result.serverPayloadSizeBytes_ = serverPayloadSizeBytes_;
        result.serverWorkIterations_ = serverWorkIterations_;
        result.serverProcessingIterations_ = serverProcessingIterations_;
        result.payload_ = payload_;
        onBuilt();
        return result;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof io.freezing.benchmark.Server.BenchmarkRequest) {
          return mergeFrom((io.freezing.benchmark.Server.BenchmarkRequest)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(io.freezing.benchmark.Server.BenchmarkRequest other) {
        if (other == io.freezing.benchmark.Server.BenchmarkRequest.getDefaultInstance()) return this;
        if (other.getId() != 0) {
          setId(other.getId());
        }
        if (other.getServerPayloadSizeBytes() != 0) {
          setServerPayloadSizeBytes(other.getServerPayloadSizeBytes());
        }
        if (other.getServerWorkIterations() != 0) {
          setServerWorkIterations(other.getServerWorkIterations());
        }
        if (other.getServerProcessingIterations() != 0) {
          setServerProcessingIterations(other.getServerProcessingIterations());
        }
        if (other.getPayload() != com.google.protobuf.ByteString.EMPTY) {
          setPayload(other.getPayload());
        }
        onChanged();
        return this;
      }

      public final boolean isInitialized() {
        return true;
      }

      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        io.freezing.benchmark.Server.BenchmarkRequest parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (io.freezing.benchmark.Server.BenchmarkRequest) e.getUnfinishedMessage();
          throw e;
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private int id_ ;
      /**
       * <code>optional int32 id = 1;</code>
       */
      public int getId() {
        return id_;
      }
      /**
       * <code>optional int32 id = 1;</code>
       */
      public Builder setId(int value) {
        
        id_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional int32 id = 1;</code>
       */
      public Builder clearId() {
        
        id_ = 0;
        onChanged();
        return this;
      }

      private int serverPayloadSizeBytes_ ;
      /**
       * <code>optional int32 server_payload_size_bytes = 2;</code>
       */
      public int getServerPayloadSizeBytes() {
        return serverPayloadSizeBytes_;
      }
      /**
       * <code>optional int32 server_payload_size_bytes = 2;</code>
       */
      public Builder setServerPayloadSizeBytes(int value) {
        
        serverPayloadSizeBytes_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional int32 server_payload_size_bytes = 2;</code>
       */
      public Builder clearServerPayloadSizeBytes() {
        
        serverPayloadSizeBytes_ = 0;
        onChanged();
        return this;
      }

      private int serverWorkIterations_ ;
      /**
       * <code>optional int32 server_work_iterations = 3;</code>
       */
      public int getServerWorkIterations() {
        return serverWorkIterations_;
      }
      /**
       * <code>optional int32 server_work_iterations = 3;</code>
       */
      public Builder setServerWorkIterations(int value) {
        
        serverWorkIterations_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional int32 server_work_iterations = 3;</code>
       */
      public Builder clearServerWorkIterations() {
        
        serverWorkIterations_ = 0;
        onChanged();
        return this;
      }

      private int serverProcessingIterations_ ;
      /**
       * <code>optional int32 server_processing_iterations = 4;</code>
       */
      public int getServerProcessingIterations() {
        return serverProcessingIterations_;
      }
      /**
       * <code>optional int32 server_processing_iterations = 4;</code>
       */
      public Builder setServerProcessingIterations(int value) {
        
        serverProcessingIterations_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional int32 server_processing_iterations = 4;</code>
       */
      public Builder clearServerProcessingIterations() {
        
        serverProcessingIterations_ = 0;
        onChanged();
        return this;
      }

      private com.google.protobuf.ByteString payload_ = com.google.protobuf.ByteString.EMPTY;
      /**
       * <code>optional bytes payload = 5;</code>
       */
      public com.google.protobuf.ByteString getPayload() {
        return payload_;
      }
      /**
       * <code>optional bytes payload = 5;</code>
       */
      public Builder setPayload(com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        payload_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional bytes payload = 5;</code>
       */
      public Builder clearPayload() {
        
        payload_ = getDefaultInstance().getPayload();
        onChanged();
        return this;
      }
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return this;
      }

      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return this;
      }


      // @@protoc_insertion_point(builder_scope:io.freezing.benchmark.BenchmarkRequest)
    }

    // @@protoc_insertion_point(class_scope:io.freezing.benchmark.BenchmarkRequest)
    private static final io.freezing.benchmark.Server.BenchmarkRequest DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new io.freezing.benchmark.Server.BenchmarkRequest();
    }

    public static io.freezing.benchmark.Server.BenchmarkRequest getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    public static final com.google.protobuf.Parser<BenchmarkRequest> PARSER =
        new com.google.protobuf.AbstractParser<BenchmarkRequest>() {
      public BenchmarkRequest parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        try {
          return new BenchmarkRequest(input, extensionRegistry);
        } catch (RuntimeException e) {
          if (e.getCause() instanceof
              com.google.protobuf.InvalidProtocolBufferException) {
            throw (com.google.protobuf.InvalidProtocolBufferException)
                e.getCause();
          }
          throw e;
        }
      }
    };

    @java.lang.Override
    public com.google.protobuf.Parser<BenchmarkRequest> getParserForType() {
      return PARSER;
    }

    public io.freezing.benchmark.Server.BenchmarkRequest getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  public interface BenchmarkResponseOrBuilder extends
      // @@protoc_insertion_point(interface_extends:io.freezing.benchmark.BenchmarkResponse)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>optional int32 id = 1;</code>
     */
    int getId();

    /**
     * <code>optional bytes payload = 2;</code>
     */
    com.google.protobuf.ByteString getPayload();
  }
  /**
   * Protobuf type {@code io.freezing.benchmark.BenchmarkResponse}
   */
  public  static final class BenchmarkResponse extends
      com.google.protobuf.GeneratedMessage implements
      // @@protoc_insertion_point(message_implements:io.freezing.benchmark.BenchmarkResponse)
      BenchmarkResponseOrBuilder {
    // Use BenchmarkResponse.newBuilder() to construct.
    private BenchmarkResponse(com.google.protobuf.GeneratedMessage.Builder builder) {
      super(builder);
    }
    private BenchmarkResponse() {
      id_ = 0;
      payload_ = com.google.protobuf.ByteString.EMPTY;
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return com.google.protobuf.UnknownFieldSet.getDefaultInstance();
    }
    private BenchmarkResponse(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry) {
      this();
      int mutable_bitField0_ = 0;
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            default: {
              if (!input.skipField(tag)) {
                done = true;
              }
              break;
            }
            case 8: {

              id_ = input.readInt32();
              break;
            }
            case 18: {

              payload_ = input.readBytes();
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw new RuntimeException(e.setUnfinishedMessage(this));
      } catch (java.io.IOException e) {
        throw new RuntimeException(
            new com.google.protobuf.InvalidProtocolBufferException(
                e.getMessage()).setUnfinishedMessage(this));
      } finally {
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return io.freezing.benchmark.Server.internal_static_io_freezing_benchmark_BenchmarkResponse_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return io.freezing.benchmark.Server.internal_static_io_freezing_benchmark_BenchmarkResponse_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              io.freezing.benchmark.Server.BenchmarkResponse.class, io.freezing.benchmark.Server.BenchmarkResponse.Builder.class);
    }

    public static final int ID_FIELD_NUMBER = 1;
    private int id_;
    /**
     * <code>optional int32 id = 1;</code>
     */
    public int getId() {
      return id_;
    }

    public static final int PAYLOAD_FIELD_NUMBER = 2;
    private com.google.protobuf.ByteString payload_;
    /**
     * <code>optional bytes payload = 2;</code>
     */
    public com.google.protobuf.ByteString getPayload() {
      return payload_;
    }

    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (id_ != 0) {
        output.writeInt32(1, id_);
      }
      if (!payload_.isEmpty()) {
        output.writeBytes(2, payload_);
      }
    }

    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;

      size = 0;
      if (id_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(1, id_);
      }
      if (!payload_.isEmpty()) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(2, payload_);
      }
      memoizedSerializedSize = size;
      return size;
    }

    private static final long serialVersionUID = 0L;
    public static io.freezing.benchmark.Server.BenchmarkResponse parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static io.freezing.benchmark.Server.BenchmarkResponse parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static io.freezing.benchmark.Server.BenchmarkResponse parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static io.freezing.benchmark.Server.BenchmarkResponse parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static io.freezing.benchmark.Server.BenchmarkResponse parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static io.freezing.benchmark.Server.BenchmarkResponse parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }
    public static io.freezing.benchmark.Server.BenchmarkResponse parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input);
    }
    public static io.freezing.benchmark.Server.BenchmarkResponse parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input, extensionRegistry);
    }
    public static io.freezing.benchmark.Server.BenchmarkResponse parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static io.freezing.benchmark.Server.BenchmarkResponse parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }

    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(io.freezing.benchmark.Server.BenchmarkResponse prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code io.freezing.benchmark.BenchmarkResponse}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:io.freezing.benchmark.BenchmarkResponse)
        io.freezing.benchmark.Server.BenchmarkResponseOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return io.freezing.benchmark.Server.internal_static_io_freezing_benchmark_BenchmarkResponse_descriptor;
      }

      protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return io.freezing.benchmark.Server.internal_static_io_freezing_benchmark_BenchmarkResponse_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                io.freezing.benchmark.Server.BenchmarkResponse.class, io.freezing.benchmark.Server.BenchmarkResponse.Builder.class);
      }

      // Construct using io.freezing.benchmark.Server.BenchmarkResponse.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessage.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
        }
      }
      public Builder clear() {
        super.clear();
        id_ = 0;

        payload_ = com.google.protobuf.ByteString.EMPTY;

        return this;
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return io.freezing.benchmark.Server.internal_static_io_freezing_benchmark_BenchmarkResponse_descriptor;
      }

      public io.freezing.benchmark.Server.BenchmarkResponse getDefaultInstanceForType() {
        return io.freezing.benchmark.Server.BenchmarkResponse.getDefaultInstance();
      }

      public io.freezing.benchmark.Server.BenchmarkResponse build() {
        io.freezing.benchmark.Server.BenchmarkResponse result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public io.freezing.benchmark.Server.BenchmarkResponse buildPartial() {
        io.freezing.benchmark.Server.BenchmarkResponse result = new io.freezing.benchmark.Server.BenchmarkResponse(this);
        result.id_ = id_;
        result.payload_ = payload_;
        onBuilt();
        return result;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof io.freezing.benchmark.Server.BenchmarkResponse) {
          return mergeFrom((io.freezing.benchmark.Server.BenchmarkResponse)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(io.freezing.benchmark.Server.BenchmarkResponse other) {
        if (other == io.freezing.benchmark.Server.BenchmarkResponse.getDefaultInstance()) return this;
        if (other.getId() != 0) {
          setId(other.getId());
        }
        if (other.getPayload() != com.google.protobuf.ByteString.EMPTY) {
          setPayload(other.getPayload());
        }
        onChanged();
        return this;
      }

      public final boolean isInitialized() {
        return true;
      }

      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        io.freezing.benchmark.Server.BenchmarkResponse parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (io.freezing.benchmark.Server.BenchmarkResponse) e.getUnfinishedMessage();
          throw e;
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private int id_ ;
      /**
       * <code>optional int32 id = 1;</code>
       */
      public int getId() {
        return id_;
      }
      /**
       * <code>optional int32 id = 1;</code>
       */
      public Builder setId(int value) {
        
        id_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional int32 id = 1;</code>
       */
      public Builder clearId() {
        
        id_ = 0;
        onChanged();
        return this;
      }

      private com.google.protobuf.ByteString payload_ = com.google.protobuf.ByteString.EMPTY;
      /**
       * <code>optional bytes payload = 2;</code>
       */
      public com.google.protobuf.ByteString getPayload() {
        return payload_;
      }
      /**
       * <code>optional bytes payload = 2;</code>
       */
      public Builder setPayload(com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        payload_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional bytes payload = 2;</code>
       */
      public Builder clearPayload() {
        
        payload_ = getDefaultInstance().getPayload();
        onChanged();
        return this;
      }
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return this;
      }

      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return this;
      }


      // @@protoc_insertion_point(builder_scope:io.freezing.benchmark.BenchmarkResponse)
    }

    // @@protoc_insertion_point(class_scope:io.freezing.benchmark.BenchmarkResponse)
    private static final io.freezing.benchmark.Server.BenchmarkResponse DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new io.freezing.benchmark.Server.BenchmarkResponse();
    }

    public static io.freezing.benchmark.Server.BenchmarkResponse getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    public static final com.google.protobuf.Parser<BenchmarkResponse> PARSER =
        new com.google.protobuf.AbstractParser<BenchmarkResponse>() {
      public BenchmarkResponse parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        try {
          return new BenchmarkResponse(input, extensionRegistry);
        } catch (RuntimeException e) {
          if (e.getCause() instanceof
              com.google.protobuf.InvalidProtocolBufferException) {
            throw (com.google.protobuf.InvalidProtocolBufferException)
                e.getCause();
          }
          throw e;
        }
      }
    };

    @java.lang.Override
    public com.google.protobuf.Parser<BenchmarkResponse> getParserForType() {
      return PARSER;
    }

    public io.freezing.benchmark.Server.BenchmarkResponse getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static com.google.protobuf.Descriptors.Descriptor
    internal_static_io_freezing_benchmark_BenchmarkRequest_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_io_freezing_benchmark_BenchmarkRequest_fieldAccessorTable;
  private static com.google.protobuf.Descriptors.Descriptor
    internal_static_io_freezing_benchmark_BenchmarkResponse_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_io_freezing_benchmark_BenchmarkResponse_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\014server.proto\022\025io.freezing.benchmark\"\230\001" +
      "\n\020BenchmarkRequest\022\n\n\002id\030\001 \001(\005\022!\n\031server" +
      "_payload_size_bytes\030\002 \001(\005\022\036\n\026server_work" +
      "_iterations\030\003 \001(\005\022$\n\034server_processing_i" +
      "terations\030\004 \001(\005\022\017\n\007payload\030\005 \001(\014\"0\n\021Benc" +
      "hmarkResponse\022\n\n\002id\030\001 \001(\005\022\017\n\007payload\030\002 \001" +
      "(\0142\336\001\n\020BenchmarkService\022`\n\tUnaryCall\022\'.i" +
      "o.freezing.benchmark.BenchmarkRequest\032(." +
      "io.freezing.benchmark.BenchmarkResponse\"" +
      "\000\022h\n\rStreamingCall\022\'.io.freezing.benchma",
      "rk.BenchmarkRequest\032(.io.freezing.benchm" +
      "ark.BenchmarkResponse\"\000(\0010\001b\006proto3"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_io_freezing_benchmark_BenchmarkRequest_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_io_freezing_benchmark_BenchmarkRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessage.FieldAccessorTable(
        internal_static_io_freezing_benchmark_BenchmarkRequest_descriptor,
        new java.lang.String[] { "Id", "ServerPayloadSizeBytes", "ServerWorkIterations", "ServerProcessingIterations", "Payload", });
    internal_static_io_freezing_benchmark_BenchmarkResponse_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_io_freezing_benchmark_BenchmarkResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessage.FieldAccessorTable(
        internal_static_io_freezing_benchmark_BenchmarkResponse_descriptor,
        new java.lang.String[] { "Id", "Payload", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
