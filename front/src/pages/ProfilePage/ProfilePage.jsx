import { useEffect, useState } from "react";
import { useOutletContext, useSearchParams } from "react-router-dom";

import styles from "./ProfilePage.module.css";
import ProfileCard from "../../components/ProfileCard/ProfileCard";
import CVInformationCard from "./components/CVInformationCard/CVInformationCard";
import { getCurrentCV } from "../../api/cv";
import { updateAboutMe } from "../../api/profiles";

function ProfilePage() {
  const { profile, profileError, setProfile } = useOutletContext();
  const [searchParams, setSearchParams] = useSearchParams();
  const isEditing = searchParams.get("edit") === "true";
  const [description, setDescription] = useState(null);
  const [saving, setSaving] = useState(false);
  const [saveError, setSaveError] = useState(null);

  function editProfile() {
    setDescription(profile.description ?? "");
    setSaveError(null);
    setSearchParams({ edit: "true" }, { replace: true });
  }

  function cancelEdit() {
    setDescription(null);
    setSaveError(null);
    setSearchParams({}, { replace: true });
  }

  async function saveProfile() {
    setSaving(true);
    setSaveError(null);
    try {
      const updatedProfile = await updateAboutMe(description ?? profile.description ?? "");
      setProfile(updatedProfile);
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
        technicalSkills={cv?.skills}
        languages={cv?.languages}
        objective={cv?.objective}
        experience={cv?.experience}
        additionalInfo={cv?.additionalInfo}
      />
    </div>
  );
}

export default ProfilePage;
