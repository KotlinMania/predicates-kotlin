import Testing
import Predicates

// Smoke test for the Kotlin → Swift Export → SPM → swift test pipeline.
@Suite("Predicates Export Tests")
struct PredicatesExportTests {
    @Test("Swift module loads and imports cleanly")
    func swiftModuleLoads() {
        #expect(true)
    }
}

