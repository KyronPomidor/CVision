import styles from "./ProfileAvatar.module.css";

function ProfileAvatar({ name, className }) {
  const initial = Array.from(name?.trim() ?? "")[0]?.toUpperCase();

  return (
    <span className={`${styles.avatar} ${className}`} aria-hidden="true">
      {initial}
    </span>
  );
}

export default ProfileAvatar;
