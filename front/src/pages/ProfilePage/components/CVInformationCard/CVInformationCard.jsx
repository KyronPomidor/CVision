import styles from "./CVInformationCard.module.css";
import typography from "../../../../Typography.module.css";

import TechnicalSkills from "./TechnicalSkills";
import Languages from "./Languages";
import Objective from "./Objective";
import Experience from "./Experience";
import AdditionalInfo from "./AdditionalInfo";

function CVInformationCard({
  technicalSkills,
  onSkillsChange,
  languages,
  onLanguagesChange,
  objective,
  experience,
  additionalInfo,
  isEditing = false,
  objectiveDraft,
  additionalInfoDraft,
  onObjectiveChange,
  onAdditionalInfoChange,
  saving,
}) {
  const hasTechnicalSkills = technicalSkills?.length > 0;
  const hasLanguages = languages?.length > 0;
  const hasObjective = Boolean(objective?.trim());
  const hasExperience = experience?.length > 0;
  const hasAdditionalInfo = Boolean(additionalInfo?.trim());
  const hasInformation =
    hasTechnicalSkills || hasLanguages || hasObjective || hasExperience || hasAdditionalInfo;

  return (
    <div className={styles.cvInformationCard}>
      <h1 className={typography.heading1}>CV Information</h1>

      <div className={styles.mainContent}>
        {(hasTechnicalSkills || isEditing) && (
          <TechnicalSkills
            key={`skills-${isEditing}`}
            skills={technicalSkills}
            isEditing={isEditing}
            onChange={onSkillsChange}
            saving={saving}
          />
        )}
        {(hasLanguages || isEditing) && (
          <Languages
            key={`languages-${isEditing}`}
            languages={languages}
            isEditing={isEditing}
            onChange={onLanguagesChange}
            saving={saving}
          />
        )}
        {(hasObjective || isEditing) && (
          <Objective
            text={objective}
            isEditing={isEditing}
            value={objectiveDraft}
            onChange={onObjectiveChange}
            saving={saving}
          />
        )}
        {hasExperience && <Experience items={experience} />}
        {(hasAdditionalInfo || isEditing) && (
          <AdditionalInfo
            text={additionalInfo}
            isEditing={isEditing}
            value={additionalInfoDraft}
            onChange={onAdditionalInfoChange}
            saving={saving}
          />
        )}
        {!hasInformation && !isEditing && (
          <p className={typography.mainText}>No CV information yet.</p>
        )}
      </div>
    </div>
  );
}

export default CVInformationCard;
