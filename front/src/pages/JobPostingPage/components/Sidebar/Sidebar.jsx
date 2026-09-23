import styles from "./Sidebar.module.css";
import JobDetails from "./JobDetails";
import CompanyBenefits from "./CompanyBenefits";

function Sidebar({ className = '', data, perks, ...props }) {
    return (
        <div className={`${className} ${styles.sidebar}`} {...props}>
            <div className={styles.sticky}>
                <JobDetails data={data} />
                <CompanyBenefits perks={perks} />
            </div>
        </div>
    );
}

export default Sidebar;