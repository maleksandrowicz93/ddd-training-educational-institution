package com.github.maleksandrowicz93.edu.domain.library.prolongationPolicyDecider;

import com.github.maleksandrowicz93.edu.domain.library.prolongPolicies.ProlongPolicies;
import com.github.maleksandrowicz93.edu.domain.library.shared.ReaderId;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Collection;

import static com.github.maleksandrowicz93.edu.domain.library.prolongPolicies.ProlongPolicies.sumProlongationPolicies;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class ProlongationPolicyDecider {

    Collection<ProlongationPolicyCalculator> prolongationPolicyCalculators;

    public ProlongPolicies choosePoliciesFor(ReaderId readerId) {
        return prolongationPolicyCalculators.stream()
                                            .map(calculator -> calculator.calculatePoliciesFor(readerId))
                                            .collect(sumProlongationPolicies());
    }
}
