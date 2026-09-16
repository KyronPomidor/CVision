import styles from "./styles/RecommandCard.module.css"
import bookLogo from "../../../../assets/book-logo.svg";
import JobCard from "./JobCard";

function RecommandCard({ jobs = [] }) {

    return (
        <div className={styles.RecommandCard}>
            <div className={styles.header}>
                <img src={bookLogo} alt="bookLogo" className={styles.headerIcon} />
                <h2 className={styles.headerTitle}>Recommended Opportunities</h2>
            </div>

            <div className={styles.jobsList}>
                {jobs.map((job) => (
                    <JobCard
                        key={job.id}
                        logo={job.logo}
                        title={job.title}
                        company={job.company}
                        postedAt={job.postedAt}
                        employmentType={job.employmentType}
                        salary={job.salary}
                        experience={job.experience}
                        matchPercent={job.matchPercent}
                        isStarred={job.isStarred}
                        onToggleStar={() => job.onToggleStar?.(job.id)}
                        onApply={() => job.onApply?.(job.id)}
                    />
                ))}
            </div>
        </div>
    )
}

export default RecommandCard
