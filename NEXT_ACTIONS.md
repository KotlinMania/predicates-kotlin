# Immediate Actions - High-Value Files

Based on AST analysis, here are the concrete next steps.

## Summary

- **Files Present:** 7/24 (29.2%)
- **Function parity:** 19/171 matched (target 67) — 11.1%
- **Class/type parity:** 12/41 matched (target 14) — 29.3%
- **Combined symbol parity:** 31/212 matched (target 81) — 14.6%
- **Average inline-code cosine:** 0.17 (function body across 5 matched files)
- **Average documentation cosine:** 0.38 (doc text across 5 matched files)
- **Cheat-zeroed Files:** 4
- **Critical Issues:** 7 files with <0.60 function similarity

## Priority 1: Fix Incomplete High-Dependency Files

No incomplete high-dependency files detected.

## Priority 2: Port Missing High-Value Files

Critical missing files (>10 dependencies):

No missing high-value files detected.

## Detailed Work Items

Every matched file is listed below with function and type symbol parity.

### 1. utils

- **Target:** `predicates.Utils [PROVENANCE-FALLBACK]`
- **Similarity:** 0.23
- **Dependents:** 9
- **Priority Score:** 9020408.0
- **Functions:** 1/3 matched (target 2)
- **Missing functions:** `new`, `fmt`
- **Types:** 1/1 matched
- **Missing types:** _none_
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `src/utils.rs` vs expected `utils.rs`
- **Proposed provenance header:** `// port-lint: source utils.rs` (current: `// port-lint: source src/utils.rs`)
- **Lint issues:** 1

### 2. boolean

- **Target:** `predicates.Boolean [PROVENANCE-FALLBACK]`
- **Similarity:** 0.33
- **Dependents:** 0
- **Priority Score:** 92006.7
- **Functions:** 7/16 matched (target 24)
- **Missing functions:** `fmt`, `find_case_true`, `find_case_true_left_fail`, `find_case_true_right_fail`, `find_case_true_fails`, `find_case_false`, `find_case_false_fails`, `find_case_false_left_fail`, `find_case_false_right_fail`
- **Types:** 4/4 matched
- **Missing types:** _none_
- **Tests:** 0/8 matched
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `src/boolean.rs` vs expected `boolean.rs`
- **Proposed provenance header:** `// port-lint: source boolean.rs` (current: `// port-lint: source src/boolean.rs`)
- **Lint issues:** 1

### 3. color

- **Target:** `predicates.Color [PROVENANCE-FALLBACK]`
- **Similarity:** 0.27
- **Dependents:** 0
- **Priority Score:** 30807.3
- **Functions:** 3/6 matched (target 5)
- **Missing functions:** `new`, `var`, `fmt`
- **Types:** 2/2 matched
- **Missing types:** _none_
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `src/color.rs` vs expected `color.rs`
- **Proposed provenance header:** `// port-lint: source color.rs` (current: `// port-lint: source src/color.rs`)
- **Lint issues:** 1

### 4. str.basics

- **Target:** `str.Basics [ZERO] [PROVENANCE-FALLBACK]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 11410.0
- **Functions:** 8/9 matched (target 32)
- **Missing functions:** `fmt`
- **Types:** 5/5 matched
- **Missing types:** _none_
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `src/str/basics.rs` vs expected `str/basics.rs`
- **Proposed provenance header:** `// port-lint: source str/basics.rs` (current: `// port-lint: source src/str/basics.rs`)
- **Lint issues:** 1

### 5. lib

- **Target:** `predicates.Lib [STUB] [PROVENANCE-FALLBACK]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 10.0
- **Functions:** 0/0 matched
- **Missing functions:** _none_
- **Types:** 0/0 matched
- **Missing types:** _none_
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `src/lib.rs` vs expected `lib.rs`
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `../predicates-core/src/lib.rs` vs expected `lib.rs`
- **Proposed provenance header:** `// port-lint: source lib.rs` (current: `// port-lint: source src/lib.rs`)
- **Proposed provenance header:** `// port-lint: source lib.rs` (current: `// port-lint: source ../predicates-core/src/lib.rs`)
- **Lint issues:** 2

### 6. prelude

- **Target:** `predicates.Prelude [ZERO] [PROVENANCE-FALLBACK]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 10.0
- **Functions:** 0/0 matched (target 4)
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 2)
- **Missing types:** _none_
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `src/prelude.rs` vs expected `prelude.rs`
- **Proposed provenance header:** `// port-lint: source prelude.rs` (current: `// port-lint: source src/prelude.rs`)
- **Lint issues:** 1

### 7. str.mod

- **Target:** `str.Mod [STUB] [PROVENANCE-FALLBACK]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 10.0
- **Functions:** 0/0 matched
- **Missing functions:** _none_
- **Types:** 0/0 matched
- **Missing types:** _none_
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `src/str/mod.rs` vs expected `str/mod.rs`
- **Proposed provenance header:** `// port-lint: source str/mod.rs` (current: `// port-lint: source src/str/mod.rs`)
- **Lint issues:** 1

## Success Criteria

For each file to be considered "complete":
- **Similarity ≥ 0.85** (Excellent threshold)
- All public APIs ported
- All tests ported
- Documentation ported
- port-lint header present

## Next Commands

```bash
# Initialize task queue for systematic porting
cd tools/ast_distance
./ast_distance --init-tasks ../../tmp/predicates/src rust ../../src/commonMain/kotlin/io/github/kotlinmania/predicates kotlin tasks.json ../../AGENTS.md

# Get next high-priority task
./ast_distance --assign tasks.json <agent-id>
```
## Reexport / Wiring Modules

These files match `reexport_modules` patterns in `.ast_distance_config.json`. They are filtered out of
normal priority and missing-file ladders because they are wiring
modules, not direct logic ports. Consult them for call-site routing;
do not treat them as the next implementation target by default.

### Missing

| Source | Expected target | Deps | Source path | Expected path |
|--------|-----------------|------|-------------|---------------|
| `float.mod` | `float.Mod` | 0 | `float/mod.rs` | `float/Mod.kt` |
| `path.mod` | `path.Mod` | 0 | `path/mod.rs` | `path/Mod.kt` |

