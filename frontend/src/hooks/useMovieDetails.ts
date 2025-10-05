import { useEffect, useState } from "react";
import apiClient from "../lib/apiClientImplementation";
import type { MovieDetails } from "../lib/types";

const useMovieDetails = (id: number) => {
    const client = apiClient;
    const [movieDetails, setMovieDetails] = useState<MovieDetails | null>(null)
    const [error, setError] = useState<string | null>(null)
    const [loading, setLoading] = useState(false)

    const loadMovieDetails = async () => {
        try { 
            setMovieDetails(await client.getMovieDetails(id)); 
        }
        catch (e) { 
            setError((e as Error).message ?? 'Failed to load trending'); 
        }
        finally{ 
            setLoading(false); 
        }
    };

    useEffect(() => { loadMovieDetails() })

    return { movieDetails, error, loading, loadMovieDetails }
}

export default useMovieDetails;