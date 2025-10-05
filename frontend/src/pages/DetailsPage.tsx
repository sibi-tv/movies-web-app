import { useParams } from "react-router-dom";
import useMovieDetails from "../hooks/useMovieDetails";
import SkeletonGrid from "../components/SkeletonGrid";

export default function DetailsPage() {

  const { id } = useParams<{ id: string }>();
  const { movieDetails, error, loading, loadMovieDetails } = useMovieDetails(Number(id));

  if (loading) return <SkeletonGrid />
  if (error) return (
    <div className="bg-red-50 border border-red-200 text-red-700 p-3 rounded-2xl flex items-center justify-between">
      <span>{error}</span>
      <button onClick={loadMovieDetails}>Retry</button>
    </div>
  )
  if (!movieDetails) return null

  return (
    <div className="space-y-4">
      <div className="flex items-center">
        <img src={movieDetails.imageUrl} className="w-20 md:w-50 lg:w-100" />
      </div>
      <div className="flex items-center gap-3">
        <h1 className="text-2xl font-bold">{movieDetails.title}</h1>
      </div>

      <div className="text-sm text-gray-600">
        <span>⭐ {movieDetails.voteAverage ?? '—'}</span>
        {movieDetails.runtime ? <span className="mx-2">• {movieDetails.runtime} min</span> : null}
        {movieDetails.releaseDate ? <span className="mx-2">• {movieDetails.releaseDate}</span> : null}
      </div>

      <p className="leading-7">{movieDetails.overview}</p>

      {Array.isArray(movieDetails.genres) && movieDetails.genres.length > 0 && (
        <div className="flex flex-wrap gap-2">
          {movieDetails.genres.map(g => <div>{g}</div>)}
        </div>
      )}
    </div>
  );
}