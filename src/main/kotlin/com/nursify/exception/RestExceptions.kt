package com.nursify.exception


open class BaseException(
    val exceptionVO: ExceptionVO = ExceptionVO(),
    val t: Throwable? = null
) : RuntimeException(exceptionVO.message, t) {
    constructor(
        message: String,
        t: Throwable? = null
    ) : this(
        exceptionVO = ExceptionVO(
            message = message,
            details = t?.message ?: t?.let { it::class.qualifiedName },
        ),
        t = t
    )
}


// 400
open class BadRequestException(
    exceptionVO: ExceptionVO = ExceptionVO(),
    t: Throwable? = null
) : BaseException(exceptionVO, t) {

    constructor(
        message: String,
        t: Throwable? = null
    ) : this(
        exceptionVO = ExceptionVO(
            message = message,
            details = t?.message ?: t?.let { it::class.qualifiedName },
        ),
        t = t
    )

    constructor(
        type: ExceptionTypesEnum = ExceptionTypesEnum.GENERIC_ERROR,
        message: String? = null,
        t: Throwable? = null
    ) : this(
        exceptionVO = ExceptionVO(
            type = type,
            message = message ?: type.message,
            details = t?.message,
        ),
        t = t
    )

    constructor(
        type: ExceptionTypesEnum = ExceptionTypesEnum.BAD_REQUEST,
        message: String = type.message,
        details: String = type.message,
    ) : this(
        exceptionVO = ExceptionVO(
            type = type,
            message = message,
            details = details,
        )
    )

}

// 401
open class UnauthorizedException(
    exceptionVO: ExceptionVO = ExceptionVO(),
    t: Throwable? = null
) : BaseException(exceptionVO, t) {
    constructor(
        message: String,
        t: Throwable? = null
    ) : this(
        exceptionVO = ExceptionVO(
            message = message,
            details = t?.message ?: t?.let { it::class.qualifiedName },
        ),
        t = t
    )

    constructor(
        type: ExceptionTypesEnum = ExceptionTypesEnum.GENERIC_ERROR,
        message: String? = null,
        t: Throwable? = null
    ) : this(
        exceptionVO = ExceptionVO(
            type = type,
            message = message ?: type.message,
            details = t?.message ?: t?.let { it::class.qualifiedName },
        ),
        t = t
    )
}

// 403
open class ForbiddenException(
    exceptionVO: ExceptionVO = ExceptionVO(),
    t: Throwable? = null
) : BaseException(exceptionVO, t) {
    constructor(
        message: String,
        t: Throwable? = null
    ) : this(
        exceptionVO = ExceptionVO(
            message = message,
            details = t?.message ?: t?.let { it::class.qualifiedName },
        ),
        t = t
    )

    constructor(
        type: ExceptionTypesEnum = ExceptionTypesEnum.GENERIC_ERROR,
        message: String? = null,
        t: Throwable? = null
    ) : this(
        exceptionVO = ExceptionVO(
            type = type,
            message = message ?: type.message,
            details = t?.message ?: t?.let { it::class.qualifiedName },
        ),
        t = t
    )
}

// 404
open class NotFoundException(
    exceptionVO: ExceptionVO = ExceptionVO(),
    t: Throwable? = null
) : BaseException(exceptionVO, t) {

    constructor(
        message: String,
        t: Throwable? = null
    ) : this(
        exceptionVO = ExceptionVO(
            message = message,
            details = t?.message ?: t?.let { it::class.qualifiedName },
        ),
        t = t
    )

    constructor(
        type: ExceptionTypesEnum = ExceptionTypesEnum.GENERIC_ERROR,
        message: String? = null,
        t: Throwable? = null
    ) : this(
        exceptionVO = ExceptionVO(
            type = type,
            message = message ?: type.message,
            details = t?.message ?: t?.let { it::class.qualifiedName },
        ),
        t = t
    )

    constructor(
        type: ExceptionTypesEnum = ExceptionTypesEnum.GENERIC_ERROR,
        message: String,
        details: String
    ) : this(
        exceptionVO = ExceptionVO(
            type = type, message = message, details = details,
        )
    )
}

// 500
open class ServerErrorException(
    exceptionVO: ExceptionVO = ExceptionVO(),
    t: Throwable? = null
) : BaseException(exceptionVO, t) {
    constructor(
        message: String,
        t: Throwable? = null
    ) : this(
        exceptionVO = ExceptionVO(
            message = message,
            details = t?.message ?: t?.let { it::class.qualifiedName },
        ),
        t = t
    )

    constructor(
        type: ExceptionTypesEnum = ExceptionTypesEnum.GENERIC_ERROR,
        message: String? = null,
        t: Throwable? = null
    ) : this(
        exceptionVO = ExceptionVO(
            type = type,
            message = message ?: type.message,
            details = t?.message ?: t?.let { it::class.qualifiedName },
        ),
        t = t
    )
}

// 501
open class NotImplementedException(
    exceptionVO: ExceptionVO = ExceptionVO(),
    t: Throwable? = null
) : BaseException(exceptionVO, t) {
    constructor(
        message: String,
        t: Throwable? = null
    ) : this(
        exceptionVO = ExceptionVO(
            message = message,
            details = t?.message ?: t?.let { it::class.qualifiedName },
        ),
        t = t
    )

    constructor(
        type: ExceptionTypesEnum = ExceptionTypesEnum.GENERIC_ERROR,
        message: String? = null,
        t: Throwable? = null
    ) : this(
        exceptionVO = ExceptionVO(
            type = type,
            message = message ?: type.message,
            details = t?.message ?: t?.let { it::class.qualifiedName },
        ),
        t = t
    )
}

// 503
open class ServiceUnavailableException(
    exceptionVO: ExceptionVO = ExceptionVO(),
    t: Throwable? = null
) : BaseException(exceptionVO, t) {
    constructor(
        message: String,
        t: Throwable? = null
    ) : this(
        exceptionVO = ExceptionVO(
            message = message,
            details = t?.message ?: t?.let { it::class.qualifiedName },
        ),
        t = t
    )

    constructor(
        type: ExceptionTypesEnum = ExceptionTypesEnum.GENERIC_ERROR,
        message: String? = null,
        t: Throwable? = null
    ) : this(
        exceptionVO = ExceptionVO(
            type = type,
            message = message ?: type.message,
            details = t?.message,
        ),
        t = t
    )
}