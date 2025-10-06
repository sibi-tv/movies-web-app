import { memo } from "react";
import type { HeartProperties } from "./properties/heartProperties"

const Heart = memo(function Heart({ active, onClick, size='md' }: HeartProperties) {
  const s = size === 'lg' ? 'text-lg' : size === 'sm' ? 'text-base' : 'text-md';

  const handleClick = (e: React.MouseEvent) => {
    e.preventDefault();
    e.stopPropagation();
    onClick(e);
  };

  return (
    <button
      aria-label="toggle favorite"
      onClick={handleClick}
      className={`${s} flex gap-2 items-center transition transform hover:scale-105 hover:cursor-pointer ${active ? 'text-rose-400' : 'text-gray-400'}`}
    >
      <div>❤︎</div> 
      {active && <div>Favorite</div>}
    </button>
  )
});

export default Heart;