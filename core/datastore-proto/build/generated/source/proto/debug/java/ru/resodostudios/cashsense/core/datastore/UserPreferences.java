// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: ru/resodostudios/cashsense/data/user_preferences.proto

// Protobuf Java Version: 3.25.0
package ru.resodostudios.cashsense.core.datastore;

/**
 * Protobuf type {@code UserPreferences}
 */
public  final class UserPreferences extends
    com.google.protobuf.GeneratedMessageLite<
        UserPreferences, UserPreferences.Builder> implements
    // @@protoc_insertion_point(message_implements:UserPreferences)
    UserPreferencesOrBuilder {
  private UserPreferences() {
  }
  public static final int DARK_THEME_CONFIG_FIELD_NUMBER = 1;
  private int darkThemeConfig_;
  /**
   * <code>.DarkThemeConfigProto dark_theme_config = 1;</code>
   * @return The enum numeric value on the wire for darkThemeConfig.
   */
  @java.lang.Override
  public int getDarkThemeConfigValue() {
    return darkThemeConfig_;
  }
  /**
   * <code>.DarkThemeConfigProto dark_theme_config = 1;</code>
   * @return The darkThemeConfig.
   */
  @java.lang.Override
  public ru.resodostudios.cashsense.core.datastore.DarkThemeConfigProto getDarkThemeConfig() {
    ru.resodostudios.cashsense.core.datastore.DarkThemeConfigProto result = ru.resodostudios.cashsense.core.datastore.DarkThemeConfigProto.forNumber(darkThemeConfig_);
    return result == null ? ru.resodostudios.cashsense.core.datastore.DarkThemeConfigProto.UNRECOGNIZED : result;
  }
  /**
   * <code>.DarkThemeConfigProto dark_theme_config = 1;</code>
   * @param value The enum numeric value on the wire for darkThemeConfig to set.
   */
  private void setDarkThemeConfigValue(int value) {
      darkThemeConfig_ = value;
  }
  /**
   * <code>.DarkThemeConfigProto dark_theme_config = 1;</code>
   * @param value The darkThemeConfig to set.
   */
  private void setDarkThemeConfig(ru.resodostudios.cashsense.core.datastore.DarkThemeConfigProto value) {
    darkThemeConfig_ = value.getNumber();

  }
  /**
   * <code>.DarkThemeConfigProto dark_theme_config = 1;</code>
   */
  private void clearDarkThemeConfig() {

    darkThemeConfig_ = 0;
  }

  public static final int USE_DYNAMIC_COLOR_FIELD_NUMBER = 2;
  private boolean useDynamicColor_;
  /**
   * <code>bool use_dynamic_color = 2;</code>
   * @return The useDynamicColor.
   */
  @java.lang.Override
  public boolean getUseDynamicColor() {
    return useDynamicColor_;
  }
  /**
   * <code>bool use_dynamic_color = 2;</code>
   * @param value The useDynamicColor to set.
   */
  private void setUseDynamicColor(boolean value) {
    
    useDynamicColor_ = value;
  }
  /**
   * <code>bool use_dynamic_color = 2;</code>
   */
  private void clearUseDynamicColor() {

    useDynamicColor_ = false;
  }

  public static ru.resodostudios.cashsense.core.datastore.UserPreferences parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data);
  }
  public static ru.resodostudios.cashsense.core.datastore.UserPreferences parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data, extensionRegistry);
  }
  public static ru.resodostudios.cashsense.core.datastore.UserPreferences parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data);
  }
  public static ru.resodostudios.cashsense.core.datastore.UserPreferences parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data, extensionRegistry);
  }
  public static ru.resodostudios.cashsense.core.datastore.UserPreferences parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data);
  }
  public static ru.resodostudios.cashsense.core.datastore.UserPreferences parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data, extensionRegistry);
  }
  public static ru.resodostudios.cashsense.core.datastore.UserPreferences parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input);
  }
  public static ru.resodostudios.cashsense.core.datastore.UserPreferences parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input, extensionRegistry);
  }

  public static ru.resodostudios.cashsense.core.datastore.UserPreferences parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return parseDelimitedFrom(DEFAULT_INSTANCE, input);
  }

  public static ru.resodostudios.cashsense.core.datastore.UserPreferences parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
  }
  public static ru.resodostudios.cashsense.core.datastore.UserPreferences parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input);
  }
  public static ru.resodostudios.cashsense.core.datastore.UserPreferences parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input, extensionRegistry);
  }

  public static Builder newBuilder() {
    return (Builder) DEFAULT_INSTANCE.createBuilder();
  }
  public static Builder newBuilder(ru.resodostudios.cashsense.core.datastore.UserPreferences prototype) {
    return DEFAULT_INSTANCE.createBuilder(prototype);
  }

  /**
   * Protobuf type {@code UserPreferences}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageLite.Builder<
        ru.resodostudios.cashsense.core.datastore.UserPreferences, Builder> implements
      // @@protoc_insertion_point(builder_implements:UserPreferences)
      ru.resodostudios.cashsense.core.datastore.UserPreferencesOrBuilder {
    // Construct using ru.resodostudios.cashsense.core.datastore.UserPreferences.newBuilder()
    private Builder() {
      super(DEFAULT_INSTANCE);
    }


    /**
     * <code>.DarkThemeConfigProto dark_theme_config = 1;</code>
     * @return The enum numeric value on the wire for darkThemeConfig.
     */
    @java.lang.Override
    public int getDarkThemeConfigValue() {
      return instance.getDarkThemeConfigValue();
    }
    /**
     * <code>.DarkThemeConfigProto dark_theme_config = 1;</code>
     * @param value The darkThemeConfig to set.
     * @return This builder for chaining.
     */
    public Builder setDarkThemeConfigValue(int value) {
      copyOnWrite();
      instance.setDarkThemeConfigValue(value);
      return this;
    }
    /**
     * <code>.DarkThemeConfigProto dark_theme_config = 1;</code>
     * @return The darkThemeConfig.
     */
    @java.lang.Override
    public ru.resodostudios.cashsense.core.datastore.DarkThemeConfigProto getDarkThemeConfig() {
      return instance.getDarkThemeConfig();
    }
    /**
     * <code>.DarkThemeConfigProto dark_theme_config = 1;</code>
     * @param value The enum numeric value on the wire for darkThemeConfig to set.
     * @return This builder for chaining.
     */
    public Builder setDarkThemeConfig(ru.resodostudios.cashsense.core.datastore.DarkThemeConfigProto value) {
      copyOnWrite();
      instance.setDarkThemeConfig(value);
      return this;
    }
    /**
     * <code>.DarkThemeConfigProto dark_theme_config = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearDarkThemeConfig() {
      copyOnWrite();
      instance.clearDarkThemeConfig();
      return this;
    }

    /**
     * <code>bool use_dynamic_color = 2;</code>
     * @return The useDynamicColor.
     */
    @java.lang.Override
    public boolean getUseDynamicColor() {
      return instance.getUseDynamicColor();
    }
    /**
     * <code>bool use_dynamic_color = 2;</code>
     * @param value The useDynamicColor to set.
     * @return This builder for chaining.
     */
    public Builder setUseDynamicColor(boolean value) {
      copyOnWrite();
      instance.setUseDynamicColor(value);
      return this;
    }
    /**
     * <code>bool use_dynamic_color = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearUseDynamicColor() {
      copyOnWrite();
      instance.clearUseDynamicColor();
      return this;
    }

    // @@protoc_insertion_point(builder_scope:UserPreferences)
  }
  @java.lang.Override
  @java.lang.SuppressWarnings({"unchecked", "fallthrough"})
  protected final java.lang.Object dynamicMethod(
      com.google.protobuf.GeneratedMessageLite.MethodToInvoke method,
      java.lang.Object arg0, java.lang.Object arg1) {
    switch (method) {
      case NEW_MUTABLE_INSTANCE: {
        return new ru.resodostudios.cashsense.core.datastore.UserPreferences();
      }
      case NEW_BUILDER: {
        return new Builder();
      }
      case BUILD_MESSAGE_INFO: {
          java.lang.Object[] objects = new java.lang.Object[] {
            "darkThemeConfig_",
            "useDynamicColor_",
          };
          java.lang.String info =
              "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0000\u0000\u0000\u0001\f\u0002\u0007" +
              "";
          return newMessageInfo(DEFAULT_INSTANCE, info, objects);
      }
      // fall through
      case GET_DEFAULT_INSTANCE: {
        return DEFAULT_INSTANCE;
      }
      case GET_PARSER: {
        com.google.protobuf.Parser<ru.resodostudios.cashsense.core.datastore.UserPreferences> parser = PARSER;
        if (parser == null) {
          synchronized (ru.resodostudios.cashsense.core.datastore.UserPreferences.class) {
            parser = PARSER;
            if (parser == null) {
              parser =
                  new DefaultInstanceBasedParser<ru.resodostudios.cashsense.core.datastore.UserPreferences>(
                      DEFAULT_INSTANCE);
              PARSER = parser;
            }
          }
        }
        return parser;
    }
    case GET_MEMOIZED_IS_INITIALIZED: {
      return (byte) 1;
    }
    case SET_MEMOIZED_IS_INITIALIZED: {
      return null;
    }
    }
    throw new UnsupportedOperationException();
  }


  // @@protoc_insertion_point(class_scope:UserPreferences)
  private static final ru.resodostudios.cashsense.core.datastore.UserPreferences DEFAULT_INSTANCE;
  static {
    UserPreferences defaultInstance = new UserPreferences();
    // New instances are implicitly immutable so no need to make
    // immutable.
    DEFAULT_INSTANCE = defaultInstance;
    com.google.protobuf.GeneratedMessageLite.registerDefaultInstance(
      UserPreferences.class, defaultInstance);
  }

  public static ru.resodostudios.cashsense.core.datastore.UserPreferences getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static volatile com.google.protobuf.Parser<UserPreferences> PARSER;

  public static com.google.protobuf.Parser<UserPreferences> parser() {
    return DEFAULT_INSTANCE.getParserForType();
  }
}
