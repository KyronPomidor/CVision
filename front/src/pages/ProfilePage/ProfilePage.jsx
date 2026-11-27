import { useEffect, useRef, useState } from "react";
import { useOutletContext, useSearchParams } from "react-router-dom";
import styles from "./ProfilePage.module.css";
import ProfileCard from "../../components/ProfileCard/ProfileCard";
import CVInformationCard from "./components/CVInformationCard/CVInformationCard";
import { getCurrentCV, uploadCV, updateCVInformation } from "../../api/cv";
import { getCurrentApplication } from "../../api/applications";
import { createProfile, updateProfile, uploadProfilePicture } from "../../api/profiles";
import { errorMessage, optionalResource } from "../../api/client";
import { mapProfile, mapCVInformation } from "../../api/mappers";

function ProfilePage() {
  const { user, profile, company, setAccount } = useOutletContext();
  const [cv, setCv] = useState(null);
  const [application, setApplication] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [uploading, setUploading] = useState(false);
  const [uploadingPhoto, setUploadingPhoto] = useState(false);
  const [actionError, setActionError] = useState(null);
  const [searchParams, setSearchParams] = useSearchParams();
  const editing = searchParams.get("edit") === "true";
  const [draft, setDraft] = useState(null);
  const [saving, setSaving] = useState(false);
  const editorRef = useRef(null);
  const fileInput = useRef(null);
  const photoInput = useRef(null);

  useEffect(() => {
    if (user.role === "EMPLOYER") return;
    let active = true;
    Promise.all([
      optionalResource(getCurrentCV()),
      optionalResource(getCurrentApplication()),
    ])
      .then(([data, mine]) => {
        if (active) {
          setCv(data);
          setApplication(mine);
        }
      })
      .catch((err) => {
        if (active) setError(err);
      })
      .finally(() => {
        if (active) setLoading(false);
      });
    return () => {
      active = false;
    };
  }, [user.role]);

  async function upload(event) {
    const file = event.target.files[0];
    if (!file) return;
    setUploading(true);
    setActionError(null);
    try {
      setCv(await uploadCV(file));
    } catch (err) {
      setActionError(errorMessage(err));
    } finally {
      setUploading(false);
      event.target.value = "";
    }
  }

  async function uploadPhoto(event) {
    const file = event.target.files[0];
    if (!file) return;
    setUploadingPhoto(true);
    setActionError(null);
    try {
      if (!profile.id) {
        const created = await createProfile({
          name: profile.name,
          location: profile.location,
          education: profile.education,
          experience: profile.experience,
          description: profile.aboutMe || "",
        });
        setAccount((previous) => ({ ...previous, profile: mapProfile(created, user) }));
      }
      const updated = mapProfile(await uploadProfilePicture(file), user);
      updated.photo += `?v=${Date.now()}`;
      setAccount((previous) => ({ ...previous, profile: updated }));
    } catch (err) {
      setActionError(errorMessage(err));
    } finally {
      setUploadingPhoto(false);
      event.target.value = "";
    }
  }

  const information = mapCVInformation(cv, profile);
  const savedDraft = { description: profile?.aboutMe || "", information };
  const currentDraft = draft ?? savedDraft;

  function editProfile() {
    setDraft(savedDraft);
    setActionError(null);
    setSearchParams({ edit: "true" }, { replace: true });
  }

  function cancelEdit() {
    setDraft(null);
    setActionError(null);
    setSearchParams({}, { replace: true });
  }

  function changeDescription(value) {
    setDraft((previous) => ({ ...(previous ?? savedDraft), description: value }));
  }

  function changeInformation(field, value) {
    setDraft((previous) => ({
      ...(previous ?? savedDraft),
      information: { ...(previous ?? savedDraft).information, [field]: value },
    }));
  }

  async function save() {
    const skills = editorRef.current?.readSkills();
    if (skills === null) return;
    const nextInformation = { ...currentDraft.information, skillExperience: skills ?? currentDraft.information.skillExperience };
    setSaving(true);
    setActionError(null);
    const data = {
      name: profile.name,
      location: profile.location,
      education: profile.education,
      experience: profile.experience,
      description: currentDraft.description.trim(),
    };
    try {
      if (!profile.id || data.description !== (profile.aboutMe || "")) {
        const updated = await (profile.id ? updateProfile(data) : createProfile(data));
        setAccount((previous) => ({ ...previous, profile: mapProfile(updated, user) }));
      }
      if (JSON.stringify(nextInformation) !== JSON.stringify(information)) {
        setCv(await updateCVInformation(nextInformation));
      }
      cancelEdit();
    } catch (err) {
      setActionError(errorMessage(err));
    } finally {
      setSaving(false);
    }
  }

  if (user.role === "EMPLOYER")
    return company ? (
      <a href={`/companies/${company.id}`}>View {company.name}</a>
    ) : (
      <p>No company profile has been created.</p>
    );
  if (loading) return <p>Loading...</p>;
  if (error) return <p role="alert">{errorMessage(error)}</p>;

  return (
    <div className={styles.ProfilePage}>
      <input ref={fileInput} type="file" hidden accept=".pdf,.docx,.txt" onChange={upload} />
      <input
        ref={photoInput}
        type="file"
        hidden
        accept="image/jpeg,image/png,image/webp"
        onChange={uploadPhoto}
      />
      {uploadingPhoto && <p role="status">Uploading photo...</p>}
      {uploading && <p>Uploading CV and extracting skills...</p>}
      {actionError && <p role="alert">{actionError}</p>}
      {application && (
        <p>
          Application #{application.id}: {application.status}
        </p>
      )}

      <ProfileCard
        variant="profile"
        name={profile.name}
        photo={profile.photo}
        email={profile.email}
        phone={profile.phone}
        location={profile.location}
        aboutMe={profile.aboutMe}
        appliedJobs={profile.appliedJobs}
        savedJobs={profile.savedJobs}
        cvViews={profile.cvViews}
        reachouts={profile.reachouts}
        onEditProfile={editProfile}
        description={currentDraft.description}
        onDescriptionChange={changeDescription}
        editing={editing}
        saving={saving || uploadingPhoto}
        onSave={save}
        onCancel={cancelEdit}
        onUploadPhoto={() => photoInput.current.click()}
        onUploadCV={uploading ? undefined : () => fileInput.current.click()}
      />

      <CVInformationCard
        editorRef={editorRef}

        education={profile.education}
        experienceSummary={profile.experience}
        information={editing ? currentDraft.information : information}
        editing={editing}
        saving={saving || uploadingPhoto}
        onChange={changeInformation}
      />
    </div>
  );
}

export default ProfilePage;
