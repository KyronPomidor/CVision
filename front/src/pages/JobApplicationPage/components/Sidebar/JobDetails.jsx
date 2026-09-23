import styles from "./JobDetails.module.css";
import typography from "../../../../Typography.module.css";

import DocIcon from "./assets/document_search.svg?react";
import HomeIcon from "./assets/home_work.svg?react";
import PinIcon from "../../../../assets/location-logo.svg?react";
import CoinIcon from "./assets/coins.svg?react";
import ClockIcon from "../../../../assets/clock-logo.svg?react";
import BriefcaseIcon from "../../../../assets/briefcase-logo.svg?react";
import GraduationIcon from "./assets/graduation_hat.svg?react";
import MailIcon from "../../../../assets/mail-icon.svg?react";

const JOB_DETAILS_CONFIG = [
  { key: "jobType", icon: DocIcon, label: "Job Type" },
  { key: "workSetting", icon: HomeIcon, label: "Work Setting" },
  { key: "location", icon: PinIcon, label: "Location" },
  { key: "salary", icon: CoinIcon, label: "Salary" },
  { key: "schedule", icon: ClockIcon, label: "Schedule" },
  { key: "experience", icon: BriefcaseIcon, label: "Experience" },
  { key: "education", icon: GraduationIcon, label: "Education" },
  { key: "contactEmail", icon: MailIcon, label: "Contact Email" },
];

const data = {
  jobType: "Full-time Contract",
  workSetting: "Remote",
  location: "Chisinau, Moldova",
  salary: "$5,200 per month",
  schedule: "Full-time",
  experience: "6 years",
  education: "Does not matter",
  contactEmail: "hr@roslin.us",
};

function JobDetails() {
  return (
    <div className={styles.container}>
      <h2 className={typography.heading2}>Job Details</h2>
      <div className={styles.grid}>
        {JOB_DETAILS_CONFIG.map(({ key, icon: Icon, label }) => (
          <div key={key} className={styles.row}>
            <Icon className={styles.icon} />
            <span className={typography.comment}>{label}</span>
            <span className={`${typography.comment} ${styles.value}`}>{data[key]}</span>
          </div>
        ))}
      </div>
    </div>
  );
}

export default JobDetails;
