package com.github.maleksandrowicz93.edu.domain.library.readerRegistration;

import com.github.maleksandrowicz93.edu.common.name.FullName;
import com.github.maleksandrowicz93.edu.domain.library.readerCatalog.ReaderType;

public record ReaderRegistrationRequest(
        FullName fullName,
        String idCardNumber,
        ReaderType readerType
) {
}
