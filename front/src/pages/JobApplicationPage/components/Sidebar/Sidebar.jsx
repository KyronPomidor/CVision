import styles from "./Sidebar.module.css";
import JobDetails from "./JobDetails";
import CompanyBenefits from "./CompanyBenefits";

function Sidebar({ className = '', ...props }) {
    return (
        <div className={`${className} ${styles.sidebar}`} {...props}>
            <div className={styles.sticky}>
                <JobDetails />
                <CompanyBenefits />
            </div>
        </div>
    );
}

export default Sidebar;