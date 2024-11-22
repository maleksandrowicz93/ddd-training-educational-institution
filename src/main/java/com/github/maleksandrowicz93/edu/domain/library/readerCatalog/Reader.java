package com.github.maleksandrowicz93.edu.domain.library.readerCatalog;

import com.github.maleksandrowicz93.edu.common.name.FullName;
import com.github.maleksandrowicz93.edu.domain.library.shared.ReaderId;
import lombok.Builder;

@Builder
public record Reader(
        ReaderId id,
        FullName fullName,
        String idCardNumber,
        ReaderType readerType
) {
}
