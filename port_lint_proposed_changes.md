# port-lint Proposed Changes

**Generated:** 2026-05-19
**Source:** tmp/predicates/src
**Target:** src/commonMain/kotlin/io/github/kotlinmania/predicates

These are review proposals only. They are emitted when a Rust -> Kotlin pair matches only after fallback normalization, so the existing `port-lint` header is not an exact provenance match.

| Target file | Current header | Proposed header | Source path | Reason |
|-------------|----------------|-----------------|-------------|--------|
| `src/commonMain/kotlin/io/github/kotlinmania/predicates/Utils.kt` | `// port-lint: source src/utils.rs` | `// port-lint: source utils.rs` | `utils.rs` | `port-lint provenance header matched only after fallback normalization: 'src/utils.rs' vs expected 'utils.rs'` |
| `src/commonMain/kotlin/io/github/kotlinmania/predicates/Boolean.kt` | `// port-lint: source src/boolean.rs` | `// port-lint: source boolean.rs` | `boolean.rs` | `port-lint provenance header matched only after fallback normalization: 'src/boolean.rs' vs expected 'boolean.rs'` |
| `src/commonMain/kotlin/io/github/kotlinmania/predicates/Color.kt` | `// port-lint: source src/color.rs` | `// port-lint: source color.rs` | `color.rs` | `port-lint provenance header matched only after fallback normalization: 'src/color.rs' vs expected 'color.rs'` |
| `src/commonMain/kotlin/io/github/kotlinmania/predicates/str/Basics.kt` | `// port-lint: source src/str/basics.rs` | `// port-lint: source str/basics.rs` | `str/basics.rs` | `port-lint provenance header matched only after fallback normalization: 'src/str/basics.rs' vs expected 'str/basics.rs'` |
| `src/commonMain/kotlin/io/github/kotlinmania/predicates/Lib.kt` | `// port-lint: source src/lib.rs` | `// port-lint: source lib.rs` | `lib.rs` | `port-lint provenance header matched only after fallback normalization: 'src/lib.rs' vs expected 'lib.rs'` |
| `src/commonMain/kotlin/io/github/kotlinmania/predicates/core/Lib.kt` | `// port-lint: source ../predicates-core/src/lib.rs` | `// port-lint: source lib.rs` | `lib.rs` | `port-lint provenance header matched only after fallback normalization: '../predicates-core/src/lib.rs' vs expected 'lib.rs'` |
| `src/commonMain/kotlin/io/github/kotlinmania/predicates/Prelude.kt` | `// port-lint: source src/prelude.rs` | `// port-lint: source prelude.rs` | `prelude.rs` | `port-lint provenance header matched only after fallback normalization: 'src/prelude.rs' vs expected 'prelude.rs'` |
| `src/commonMain/kotlin/io/github/kotlinmania/predicates/str/Mod.kt` | `// port-lint: source src/str/mod.rs` | `// port-lint: source str/mod.rs` | `str/mod.rs` | `port-lint provenance header matched only after fallback normalization: 'src/str/mod.rs' vs expected 'str/mod.rs'` |
