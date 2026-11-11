import { useEffect, useState } from "react";
import { useOutletContext } from "react-router-dom";

import styles from "./ProfilePage.module.css";
import ProfileCard from "../../components/ProfileCard/ProfileCard";
import CVInformationCard from "./components/CVInformationCard/CVInformationCard";
import { getCurrentCV } from "../../api/cv";

function ProfilePage() {
  const { profile, profileError } = useOutletContext();
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
        onEditProfile={() => console.log("edit clicked")}
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
