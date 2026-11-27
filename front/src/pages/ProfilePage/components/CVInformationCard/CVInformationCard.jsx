import styles from "./CVInformationCard.module.css";
import typography from "../../../../Typography.module.css";
import TechnicalSkills from "./TechnicalSkills";
import Languages from "./Languages";
import Objective from "./Objective";
import Experience from "./Experience";
import AdditionalInfo from "./AdditionalInfo";

function CVInformationCard({
  information,
  editorRef,
  editing,
  onChange,
  saving,
  education,
  experienceSummary,
}) {
  const { skillExperience: skills, languages, objective, experience, additionalInfo } = information;
  const hasInformation =
    skills.length ||
    languages.length ||
    objective.trim() ||
    experience.length ||
    additionalInfo.trim() ||
    education ||
    experienceSummary;

  return (
    <div className={styles.cvInformationCard}>
      <h1 className={typography.heading1}>CV Information</h1>
      <div className={styles.mainContent}>
        {!editing && !hasInformation && (
          <p className={typography.mainText}>No CV information yet.</p>
        )}
        {education && <p className={typography.mainText}>Education: {education}</p>}
        {experienceSummary && (
          <p className={typography.mainText}>Experience: {experienceSummary}</p>
        )}
        {(editing || skills.length > 0) && (
          <TechnicalSkills
            key={`skills-${editing}`}
            skills={skills}
            editorRef={editorRef}
            editing={editing}
            saving={saving}
            onChange={(value) => onChange("skillExperience", value)}
          />
        )}
        {(editing || languages.length > 0) && (
          <Languages
            key={`languages-${editing}`}
            languages={languages}
            editing={editing}
            saving={saving}
            onChange={(value) => onChange("languages", value)}
          />
        )}
        {(editing || objective.trim()) && (
          <Objective
            text={objective}
            editing={editing}
            value={objective}
            saving={saving}
            onChange={(value) => onChange("objective", value)}
          />
        )}
        {(editing || experience.length > 0) && (
          <Experience
            key={`experience-${editing}`}
            items={experience}
            editing={editing}
            saving={saving}
            onChange={(value) => onChange("experience", value)}
          />
        )}
        {(editing || additionalInfo.trim()) && (
          <AdditionalInfo
            text={additionalInfo}
            editing={editing}
            value={additionalInfo}
            saving={saving}
            onChange={(value) => onChange("additionalInfo", value)}
          />
        )}
      </div>
    </div>
  );
}

export default CVInformationCard;
