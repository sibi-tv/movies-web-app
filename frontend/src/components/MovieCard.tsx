import { memo } from "react";
import { Link } from "react-router-dom";
import type { cardProperties } from "./properties/cardProperties";
import Heart from "./Heart";

const MovieCard = memo(function MovieCard({
  movie,
  isFavorite,
  onToggleFavorite,
}: cardProperties) {
  return (
    <Link
      to={`/movies/${movie.id}`}
      className="flex flex-col items-center justify-center rounded-2xl border border-gray-200 p-4"
    >
      <img
        src={movie.imageUrl}
        alt={movie.title}
        className="w-50 aspect-[2/3] object-cover rounded-lg"
      />
      <div className="mt-2 text-md font-semibold line-clamp-2">
        {movie.title}
      </div>
      <div className="flex gap-3 pt-3">
        <div className="mt-0.5 text-sm text-gray-500">
          ⭐ {movie.voteAverage ?? "—"}
        </div>
        <Heart active={isFavorite} onClick={() => onToggleFavorite(movie.id)} />
      </div>
    </Link>
  );
});

export default MovieCard;
