package com.nursify.exception


enum class ExceptionTypesEnum(val message: String) {

    // GENERIC
    GENERIC_ERROR("Something unexpected happened! :D"),

    // 400 - Bad Request
    BAD_REQUEST("There's something wrong with your request."),
    ALIAS_NOT_UNIQUE("The resource alias must be unique"),
    QUERY_SYNTAX_ERROR("Query syntax error."),
    MISSING_DATASOURCE("No datasource selected."),
    EXTENSION_FORMAT_NOT_SUPPORTED("Extension format not supported."),
    METADATA_ERROR("Can not update metadata at this time."),

    /** QueryParamException **/
    MISSING_PARAM_VALUE("Missing parameter value."),
    MISSING_PARAM("Missing query parameter."),
    PARAM_NOT_SUPPORTED("Parameter not supported on this query."),

    // 401 - Unauthorized
    INVALID_TOKEN("Invalid token."),
    LOGIN_BAD_REQUEST("Wrong username or password, try again"),

    // 403 - Forbidden
    OPERATION_NOT_ALLOWED("This operation is not allowed."),

    // 404 - Not Found
    ACL_NOT_FOUND("Permission error."),
    RESOURCE_NOT_FOUND("Resource not found."),
    AGENT_NOT_FOUND("Agent not found."),
    FILE_NOT_FOUND("File not found"),

    // 500 - Server Error
    EXECUTOR_ERROR("Error executing query."),
    S3_ERROR("Error executing query."),

    // 501 - Not Implemented

    // 503 - Service Unavailable
    DATABASE_CONNECTION_ERROR("Could not connect to database.")


}