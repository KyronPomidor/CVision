import LegalLayout from "../../components/LegalLayout/LegalLayout"

function PrivacyPolicyPage() {
    return (
        <LegalLayout title="Privacy Policy" updatedAt="September 16, 2026">
            <p>
                CVision ("we", "us", "our") provides an AI-powered platform that matches your CV
                to relevant job opportunities. This Privacy Policy explains what information we
                collect, how we use it, and the choices you have.
            </p>

            <h2>1. Information We Collect</h2>
            <p>When you create an account and use CVision, we collect:</p>
            <ul>
                <li>Account details you provide, such as your name, email address, and phone number.</li>
                <li>CV and profile content, including work experience, skills, education, and any documents you upload.</li>
                <li>Job preferences and activity, such as searches, filters, and jobs you view, save, or apply to.</li>
                <li>Technical data, such as device information, IP address, and log data, collected automatically when you use the site.</li>
            </ul>

            <h2>2. How We Use Your Information</h2>
            <p>We use your information to:</p>
            <ul>
                <li>Analyze your CV with our AI matching engine and generate compatibility scores against job postings.</li>
                <li>Recommend jobs and companies relevant to your profile.</li>
                <li>Operate, maintain, and improve the CVision platform, including our matching algorithms.</li>
                <li>Communicate with you about your account, applications, and relevant opportunities.</li>
                <li>Detect, prevent, and address fraud, abuse, or security issues.</li>
            </ul>

            <h2>3. AI-Based CV Matching</h2>
            <p>
                CVision uses automated, AI-based processing to compare your CV against job listings
                and produce a match score. This processing is used to help you discover relevant
                opportunities faster; it does not make final hiring decisions, which remain with
                employers. You can review and edit your profile at any time to refine your matches.
            </p>

            <h2>4. Sharing Your Information</h2>
            <p>We do not sell your personal information. We may share your information with:</p>
            <ul>
                <li>Employers and recruiters, when you apply to a job or choose to make your profile visible to them.</li>
                <li>Service providers who help us operate the platform (e.g. hosting, analytics), under confidentiality obligations.</li>
                <li>Authorities, where required by law or to protect the rights, safety, and security of CVision or others.</li>
            </ul>

            <h2>5. Data Retention</h2>
            <p>
                We retain your account and CV data for as long as your account is active, or as
                needed to provide you the service. You can request deletion of your account and
                associated data at any time; we will remove it, except where retention is required
                by law.
            </p>

            <h2>6. Your Rights</h2>
            <p>Depending on your location, you may have the right to:</p>
            <ul>
                <li>Access, correct, or delete the personal data we hold about you.</li>
                <li>Export a copy of your data.</li>
                <li>Withdraw consent to certain processing, such as AI-based matching.</li>
                <li>Object to or restrict certain uses of your data.</li>
            </ul>
            <p>
                To exercise these rights, contact us using the details below.
            </p>

            <h2>7. Cookies</h2>
            <p>
                We use cookies and similar technologies to keep you signed in, remember your
                preferences, and understand how CVision is used. You can control cookies through
                your browser settings.
            </p>

            <h2>8. Security</h2>
            <p>
                We use reasonable technical and organizational measures to protect your information.
                No method of transmission or storage is completely secure, so we cannot guarantee
                absolute security.
            </p>

            <h2>9. Children's Privacy</h2>
            <p>
                CVision is not directed at individuals under 16, and we do not knowingly collect
                personal information from children.
            </p>

            <h2>10. Changes to This Policy</h2>
            <p>
                We may update this Privacy Policy from time to time. If we make material changes,
                we will notify you through the platform or by email.
            </p>

            <h2>11. Contact Us</h2>
            <p>
                If you have questions about this Privacy Policy or how we handle your data, contact
                us at <a href="mailto:privacy@cvision.app">privacy@cvision.app</a>.
            </p>
        </LegalLayout>
    )
}

export default PrivacyPolicyPage
