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
  {
    key: "salary",
    icon: CoinIcon,
    label: "Salary",
    format: (value) => `$${value.toLocaleString()} per month`,
  },
  { key: "schedule", icon: ClockIcon, label: "Schedule" },
  { key: 
    "experience", 
    icon: BriefcaseIcon, 
    label: "Experience",
    format: (value) => `${value} years`
  },
  { key: "education", icon: GraduationIcon, label: "Education" },
  { key: "contactEmail", icon: MailIcon, label: "Contact Email" },
];

function JobDetails({ data }) {
  return (
    <div className={styles.container}>
      <h2 className={typography.heading2}>Job Details</h2>
      <div className={styles.grid}>
        {JOB_DETAILS_CONFIG.map(({ key, icon: Icon, label, format }) => {
          const rawValue = data[key];
          const displayValue = format ? format(rawValue) : rawValue;

          return (
          <div key={key} className={styles.row}>
            <Icon className={styles.icon} />
            <span className={typography.comment}>{label}</span>
            <span className={`${typography.comment} ${styles.value}`}>{displayValue}</span>
          </div>
        )})}
      </div>
    </div>
  );
}

export default JobDetails;
