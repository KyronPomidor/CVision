import { useEffect, useState } from "react";
import { useOutletContext, useSearchParams } from "react-router-dom";

import styles from "./ProfilePage.module.css";
import ProfileCard from "../../components/ProfileCard/ProfileCard";
import CVInformationCard from "./components/CVInformationCard/CVInformationCard";
import { getCurrentCV, updateCVInformation } from "../../api/cv";
import { updateAboutMe } from "../../api/profiles";

function ProfilePage() {
  const { profile, profileError, setProfile } = useOutletContext();
  const [searchParams, setSearchParams] = useSearchParams();
  const isEditing = searchParams.get("edit") === "true";
  const [description, setDescription] = useState(null);
  const [objective, setObjective] = useState(null);
  const [additionalInfo, setAdditionalInfo] = useState(null);
  const [skills, setSkills] = useState(null);
  const [languages, setLanguages] = useState(null);
  const [saving, setSaving] = useState(false);
  const [saveError, setSaveError] = useState(null);

  function editProfile() {
    setDescription(profile.description ?? "");
    setSkills(cv?.skills ?? []);
    setLanguages(cv?.languages ?? []);
    setObjective(cv?.objective ?? "");
    setAdditionalInfo(cv?.additionalInfo ?? "");
    setSaveError(null);
    setSearchParams({ edit: "true" }, { replace: true });
  }

  function cancelEdit() {
    setDescription(null);
    setSkills(null);
    setLanguages(null);
    setObjective(null);
    setAdditionalInfo(null);
    setSaveError(null);
    setSearchParams({}, { replace: true });
  }

  async function saveProfile() {
    setSaving(true);
    setSaveError(null);
    try {
      const updatedProfile = await updateAboutMe(description ?? profile.description ?? "");
      setProfile(updatedProfile);
      if (cv) {
        const updatedCv = await updateCVInformation(cv.id, {
          skills: skills ?? cv.skills ?? [],
          languages: languages ?? cv.languages ?? [],
          objective: objective ?? cv.objective ?? "",
          additionalInfo: additionalInfo ?? cv.additionalInfo ?? "",
        });
        setCv(updatedCv);
      }
      cancelEdit();
    } catch {
      setSaveError("Could not save your changes. Please try again.");
    } finally {
      setSaving(false);
    }
  }
  const [cv, setCv] = useState();
  const [error, setError] = useState(null);

  useEffect(() => {
    getCurrentCV()
      .then(setCv)
      .catch((err) => {
        if (err.response?.status === 404) {
          setCv(null);
        } else {
          setError(err);
        }
      });
  }, []);

  if (profileError || error) return <p>Something went wrong.</p>;
  if (cv === undefined || !profile) return <p>Loading...</p>;

  return (
    <div className={styles.ProfilePage}>
      <ProfileCard
        variant="profile"
        name={profile.name}
        phone={profile.phone}
        email={profile.email}
        location={profile.location}
        aboutMe={profile.description}
        onEditProfile={editProfile}
        isEditing={isEditing}
        description={description ?? profile.description ?? ""}
        onDescriptionChange={setDescription}
        onSaveProfile={saveProfile}
        onCancelEdit={cancelEdit}
        saving={saving}
        saveError={saveError}
        onUploadCV={() => console.log("upload cv clicked")}
      />

      <CVInformationCard
        technicalSkills={isEditing ? (skills ?? cv?.skills) : cv?.skills}
        onSkillsChange={setSkills}
        languages={isEditing ? (languages ?? cv?.languages) : cv?.languages}
        onLanguagesChange={setLanguages}
        objective={cv?.objective}
        experience={cv?.experience}
        additionalInfo={cv?.additionalInfo}
        isEditing={isEditing && Boolean(cv)}
        objectiveDraft={objective ?? cv?.objective ?? ""}
        additionalInfoDraft={additionalInfo ?? cv?.additionalInfo ?? ""}
        onObjectiveChange={setObjective}
        onAdditionalInfoChange={setAdditionalInfo}
        saving={saving}
      />
    </div>
  );
}

export default ProfilePage;
