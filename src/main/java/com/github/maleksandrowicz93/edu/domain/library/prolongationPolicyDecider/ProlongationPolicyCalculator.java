package com.github.maleksandrowicz93.edu.domain.library.prolongationPolicyDecider;

import com.github.maleksandrowicz93.edu.domain.library.prolongPolicies.ProlongPolicies;
import com.github.maleksandrowicz93.edu.domain.library.shared.ReaderId;

interface ProlongationPolicyCalculator {

    ProlongPolicies calculatePoliciesFor(ReaderId readerId);
}
