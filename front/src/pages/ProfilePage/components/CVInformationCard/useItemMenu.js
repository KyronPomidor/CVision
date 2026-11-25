import { useEffect, useRef, useState } from "react";

export default function useItemMenu() {
  const [selected, setSelected] = useState(null);
  const [draft, setDraft] = useState(null);
  const ref = useRef(null);

  useEffect(() => {
    if (selected === null) return;
    function onPointerDown(event) {
      if (!ref.current?.contains(event.target)) setSelected(null);
    }
    function onKeyDown(event) {
      if (event.key === "Escape") setSelected(null);
    }
    document.addEventListener("pointerdown", onPointerDown);
    document.addEventListener("keydown", onKeyDown);
    return () => {
      document.removeEventListener("pointerdown", onPointerDown);
      document.removeEventListener("keydown", onKeyDown);
    };
  }, [selected]);

  function open(index) {
    setSelected(index);
    setDraft(null);
  }

  function close() {
    setSelected(null);
    setDraft(null);
  }

  return { selected, draft, setDraft, ref, open, close };
}
