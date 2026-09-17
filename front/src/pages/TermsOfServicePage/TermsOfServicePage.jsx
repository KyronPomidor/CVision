import LegalLayout from "../../components/LegalLayout/LegalLayout"

function TermsOfServicePage() {
    return (
        <LegalLayout title="Terms of Service" updatedAt="September 16, 2026">
            <p>
                These Terms of Service ("Terms") govern your access to and use of CVision, an
                AI-powered platform that matches your CV to job opportunities. By creating an
                account or using CVision, you agree to these Terms.
            </p>

            <h2>1. Description of Service</h2>
            <p>
                CVision lets you build a profile from your CV, browse job listings, and receive
                AI-generated compatibility scores between your profile and available jobs. CVision
                is a discovery and matching tool; it does not employ you and is not a party to any
                employment relationship between you and an employer.
            </p>

            <h2>2. Eligibility and Accounts</h2>
            <ul>
                <li>You must provide accurate, current information when creating your account.</li>
                <li>You are responsible for keeping your login credentials confidential and for all activity under your account.</li>
                <li>You must be legally permitted to seek employment in your relevant jurisdiction to use the job-matching features.</li>
            </ul>

            <h2>3. Your Content</h2>
            <p>
                You retain ownership of the CV, profile information, and any other content you
                submit ("Your Content"). By submitting Your Content, you grant CVision a
                non-exclusive, worldwide license to use, process, and analyze it in order to
                operate the matching engine, display your profile to employers you apply to or
                make yourself visible to, and improve our services.
            </p>

            <h2>4. AI Matching Disclaimer</h2>
            <p>
                Compatibility scores and job recommendations are generated automatically and are
                provided for informational purposes only. CVision does not guarantee the accuracy
                of any match, that you will be contacted by an employer, or that you will obtain
                any job through the platform. Hiring decisions are made solely by employers.
            </p>

            <h2>5. Acceptable Use</h2>
            <p>You agree not to:</p>
            <ul>
                <li>Submit false, misleading, or someone else's information as your own.</li>
                <li>Post job listings that are fraudulent, discriminatory, or unlawful.</li>
                <li>Scrape, reverse-engineer, or interfere with the platform or its AI systems.</li>
                <li>Use CVision to harass, spam, or harm other users or employers.</li>
            </ul>

            <h2>6. Job Listings from Third Parties</h2>
            <p>
                Some job listings on CVision may be provided by third-party employers or partners.
                We do our best to keep listings accurate, but we are not responsible for the
                content, accuracy, or availability of third-party listings, or for any interactions
                you have with employers.
            </p>

            <h2>7. Termination</h2>
            <p>
                You may stop using CVision and delete your account at any time. We may suspend or
                terminate your access if you violate these Terms or misuse the platform.
            </p>

            <h2>8. Disclaimers and Limitation of Liability</h2>
            <p>
                CVision is provided "as is" without warranties of any kind. To the fullest extent
                permitted by law, CVision and its team are not liable for indirect, incidental, or
                consequential damages arising from your use of the platform, including any
                employment outcome or lack thereof.
            </p>

            <h2>9. Changes to These Terms</h2>
            <p>
                We may update these Terms from time to time. Continued use of CVision after changes
                take effect constitutes acceptance of the updated Terms.
            </p>

            <h2>10. Contact Us</h2>
            <p>
                Questions about these Terms can be sent to{" "}
                <a href="mailto:support@cvision.app">support@cvision.app</a>.
            </p>
        </LegalLayout>
    )
}

export default TermsOfServicePage
