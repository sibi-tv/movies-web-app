import { Clapperboard } from "lucide-react";
import { Link } from "react-router-dom";

export default function Navbar() {
  return (
    <header className="border-b border-neutral-800">
      <div className="mx-auto max-w-6xl px-4 py-4 gap-2 flex items-center justify-center">
        <Clapperboard />
        <Link to="/" className="font-bold" >
          TMDB Movie List
        </Link>
      </div>
    </header>
  );
}